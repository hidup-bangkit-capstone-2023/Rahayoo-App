package com.bangkit.rahayoo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.databinding.FragmentProfileBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.ViewModelFactory
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvEditProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }

        viewModel.userData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // Show Loading Indicator
                }

                is UiState.Success -> {
                    val userData = uiState.data

                    Glide.with(requireContext())
                        .load(userData.avatarUrl)
                        .circleCrop()
                        .placeholder(R.drawable.ic_logo_non_transparent)
                        .into(binding.ivProfile)

                    binding.tvProfileName.text = userData.name
                }

                is UiState.Error -> {
                    // Show Error Message
                }
            }
        }
    }
}