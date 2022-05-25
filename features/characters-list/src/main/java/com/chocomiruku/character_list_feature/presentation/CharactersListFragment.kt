package com.chocomiruku.character_list_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.chocomiruku.character_list_feature.R
import com.chocomiruku.character_list_feature.databinding.FragmentCharactersListBinding
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import com.google.android.material.snackbar.Snackbar


class CharactersListFragment : Fragment() {

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            CharactersListViewModelFactory(activity.application)
        )[CharactersListViewModel::class.java]
    }

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)

        setup()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged { query ->
            viewModel.searchCharacters(query)
        }
    }

    private fun bindViewModel() {
        viewModel.charactersStateFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressIndicator.isVisible = true
                        binding.charactersList.isGone = true
                    }
                    is Resource.Success -> {
                        updateCharactersData(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressIndicator.isGone = true
                        showSnackBarConnectionError()
                    }
                }
            }
    }

    private fun updateCharactersData(characters: List<Character>?) {
        binding.progressIndicator.isGone = true
        binding.charactersList.isVisible = true
        characterAdapter.submitList(characters)
    }

    private fun setup() {
        binding.charactersList.adapter = characterAdapter
        bindViewModel()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.characters_list)
    }

    private fun showSnackBarConnectionError() {
        Snackbar.make(binding.charactersList, R.string.connection_error, Snackbar.LENGTH_LONG)
            .show()
    }
}