package com.chocomiruku.character_list_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.chocomiruku.character_list_feature.R
import com.chocomiruku.character_list_feature.databinding.FragmentCharactersListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersListViewModel by viewModels()

    private val characterPagingAdapter: CharacterPagingAdapter by lazy {
        CharacterPagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        bindPagingAdapter()
        bindSearch(searchView)
    }

    private fun setup() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.characters_list)
    }

    private fun bindPagingAdapter() {
        binding.retryButton.setOnClickListener { characterPagingAdapter.retry() }

        characterPagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val header = CharactersLoadStateAdapter { characterPagingAdapter.retry() }
        bindLoadingStates(header)

        binding.charactersList.adapter = characterPagingAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = CharactersLoadStateAdapter { characterPagingAdapter.retry() }
        )
    }

    private fun bindCharactersList(query: String) {
        lifecycleScope.launch {
            viewModel.getCharactersFlow(query)
                .collectLatest(characterPagingAdapter::submitData)
        }
    }

    @OptIn(FlowPreview::class)
    private fun bindSearch(searchView: SearchView) {
        lifecycleScope.launch {
            searchView.getQueryTextChangeStateFlow()
                .debounce(SEARCH_TIMEOUT)
                .distinctUntilChanged()
                .collectLatest { query ->
                    bindCharactersList(query)
                }
        }
    }

    private fun bindLoadingStates(header: CharactersLoadStateAdapter) {
        lifecycleScope.launch {
            characterPagingAdapter.loadStateFlow.collectLatest { loadState ->
                // Show header when initial refresh failed but cache is not empty
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && characterPagingAdapter.itemCount > 0 }
                    ?: loadState.prepend

                binding.charactersList.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading

                binding.progressIndicator.isVisible =
                    loadState.mediator?.refresh is LoadState.Loading

                val isListEmpty = loadState.source.refresh is LoadState.NotLoading
                        && loadState.mediator?.refresh is LoadState.NotLoading
                        && characterPagingAdapter.itemCount == 0
                binding.emptyList.isVisible = isListEmpty

                val initialRefreshFailed =
                    loadState.mediator?.refresh is LoadState.Error && characterPagingAdapter.itemCount == 0
                binding.retryButton.isVisible = initialRefreshFailed

                val refreshFailed =
                    loadState.mediator?.refresh is LoadState.Error || loadState.source.refresh is LoadState.Error

                if (initialRefreshFailed || refreshFailed) {
                    showSnackBarError()
                    binding.charactersList.scrollToPosition(TOP_POSITION)
                }
            }
        }
    }

    private fun showSnackBarError() {
        Snackbar.make(
            binding.retryButton,
            R.string.network_connection_error,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    private companion object {
        const val TOP_POSITION = 0
        const val SEARCH_TIMEOUT = 300L
    }
}