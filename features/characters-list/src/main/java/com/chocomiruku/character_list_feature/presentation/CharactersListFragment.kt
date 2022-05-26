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
import androidx.paging.filter
import androidx.recyclerview.widget.RecyclerView
import com.chocomiruku.character_list_feature.R
import com.chocomiruku.character_list_feature.databinding.FragmentCharactersListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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
        bindLoadingStates(CharactersLoadStateAdapter { characterPagingAdapter.retry() })
        bindCharactersList(EMPTY_QUERY)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView


        searchView.onQueryTextChanged { query ->
            bindCharactersList(query)
            binding.charactersList.scrollToPosition(0)
        }
    }

    private fun setup() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.characters_list)

        binding.retryButton.setOnClickListener { characterPagingAdapter.retry() }

        characterPagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.charactersList.adapter = characterPagingAdapter.withLoadStateHeaderAndFooter(
            header = CharactersLoadStateAdapter { characterPagingAdapter.retry() },
            footer = CharactersLoadStateAdapter { characterPagingAdapter.retry() }
        )
    }

    private fun bindCharactersList(query: String) {
        lifecycleScope.launch {
            viewModel.charactersPagingDataFlow
                .distinctUntilChanged()
                .collect { pagingData ->
                    characterPagingAdapter.submitData(pagingData.filter { character ->
                        character.name.lowercase().contains(query.lowercase())
                    })
                    binding.charactersList.scrollToPosition(0)
                }
        }
    }

    private fun bindLoadingStates(header: CharactersLoadStateAdapter) {
        lifecycleScope.launch {
            characterPagingAdapter.loadStateFlow.collect { loadState ->
                // Shows header when initial refresh fails
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && characterPagingAdapter.itemCount > 0 }
                    ?: loadState.prepend


                binding.charactersList.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading

                binding.progressIndicator.isVisible =
                    loadState.mediator?.refresh is LoadState.Loading

                binding.retryButton.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && characterPagingAdapter.itemCount == 0
            }
        }


        lifecycleScope.launch {
            characterPagingAdapter.loadStateFlow
                .filter { loadState ->
                    loadState.mediator?.refresh is LoadState.Error
                }
                .collect {
                    showSnackBarConnectionError()
                }
        }
    }

    private fun showSnackBarConnectionError() {
        Snackbar.make(binding.charactersList, R.string.connection_error, Snackbar.LENGTH_LONG)
            .show()
    }
}