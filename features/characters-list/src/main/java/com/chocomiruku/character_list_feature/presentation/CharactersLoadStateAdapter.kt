package com.chocomiruku.character_list_feature.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chocomiruku.character_list_feature.R
import com.chocomiruku.character_list_feature.databinding.LoadStateItemBinding

class CharactersLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharactersLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateViewHolder {
        return CharactersLoadStateViewHolder.create(parent, retry)
    }
}

class CharactersLoadStateViewHolder(
    private val binding: LoadStateItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryBtn.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorText.text = itemView.context.getString(R.string.generic_error)
        }
        binding.progressIndicator.isVisible = loadState is LoadState.Loading
        binding.retryBtn.isVisible = loadState is LoadState.Error
        binding.errorText.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_item, parent, false)
            val binding = LoadStateItemBinding.bind(view)
            return CharactersLoadStateViewHolder(binding, retry)
        }
    }
}