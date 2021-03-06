package com.chocomiruku.character_list_feature.presentation

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val searchQuery = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            this@getQueryTextChangeStateFlow.clearFocus()
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            searchQuery.value = newText ?: ""
            return true
        }
    })
    return searchQuery
}