package com.chocomiruku.character_list_feature.presentation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chocomiruku.character_list_feature.R
import com.chocomiruku.character_list_feature.databinding.ListItemCharacterBinding
import com.chocomiruku.core.domain.Character

class CharacterAdapter :
    ListAdapter<Character, CharacterAdapter.ViewHolder>(FactDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener {
            holder.navigateToCharacter(character, holder.itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun navigateToCharacter(
            character: Character,
            view: View
        ) {
            val navOptions =
                NavOptions.Builder()
                    .setEnterAnim(R.anim.enter_anim)
                    .setExitAnim(R.anim.exit_anim)
                    .setPopEnterAnim(R.anim.pop_enter_anim)
                    .setPopExitAnim(R.anim.pop_exit_anim)
                    .build()


            val uri = Uri.parse("android-app://character-details-feature/${character.id}")
            view.findNavController().navigate(uri, navOptions)
        }

        fun bind(character: Character) {
            binding.nameText.text = character.name

            val imgUri = character.profilePicUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(binding.characterPic.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_anim)
                        .centerInside()
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.characterPic)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemCharacterBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class FactDiffCallback :
        DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}