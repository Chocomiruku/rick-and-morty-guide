package com.chocomiruku.character_details_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chocomiruku.core_ui.R
import com.chocomiruku.character_details_feature.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        bindViewModel()

        return binding.root
    }

    private fun bindViewModel() {
        viewModel.getCharacter(arguments?.getString(CHARACTER_ID_KEY)?.toInt() ?: 0)
            .observe(viewLifecycleOwner) {
                binding.nameText.text = it.name
                updateTitle(it.name)

                binding.genderText.text = it.gender
                binding.statusText.text = it.status
                binding.speciesText.text = it.species
                binding.creationDateText.text = it.creationDate

                val imgUri =
                    it.profilePicUrl.toUri().buildUpon().scheme(SCHEME).build()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val SCHEME = "https"
        const val CHARACTER_ID_KEY = "characterId"
    }
}