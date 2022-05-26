package com.chocomiruku.character_details_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chocomiruku.character_details_feature.R
import com.chocomiruku.character_details_feature.databinding.FragmentCharacterDetailsBinding


class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            CharacterDetailsViewModelFactory(
                arguments?.getString("characterId")?.toInt() ?: 0,
                activity.application
            )
        )[CharacterDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        bindViewModel()

        return binding.root
    }

    private fun bindViewModel() {
        viewModel.character
            .observe(viewLifecycleOwner) {
                binding.nameText.text = it.name
                updateTitle(it.name)

                binding.genderText.text = it.gender
                binding.statusText.text = it.status
                binding.speciesText.text = it.species
                binding.creationDateText.text = it.creationDate
                val imgUri =
                    it.profilePicUrl.toUri().buildUpon().scheme("https").build()
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
    }

    private fun updateTitle(characterName: String) {
        (activity as AppCompatActivity).supportActionBar?.title = characterName
    }
}