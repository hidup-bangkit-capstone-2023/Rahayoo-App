package com.bangkit.rahayoo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.databinding.FragmentHomeBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.ViewModelFactory
import com.bumptech.glide.Glide

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTakeStressTest.setOnClickListener(this)
        binding.btnNotification.setOnClickListener(this)

        viewModel.user.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // Show Loading Progress
                }

                is UiState.Success -> {
                    val userData = uiState.data

                    binding.apply {
                        Glide.with(requireContext())
                            .load(userData.avatarUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_app_logo)
                            .into(ivProfile)

                        tvProfileName.text = userData.name
                    }
                }

                is UiState.Error -> {
                    // Show Error Message
                }
            }
        }

        viewModel.userStressLevelData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // Show Loading Progress
                    changeWeeklyCalendarLoadingVisibility(true)
                }

                is UiState.Success -> {
                    changeWeeklyCalendarLoadingVisibility(false)
                    val userStressLevelData = uiState.data
                    binding.apply {
                        tvPercentageStressWeek.text = getString(R.string.stress_level_weekly_percentage, userStressLevelData.stressValue)
                    }
                }

                is UiState.Error -> {
                    // Show Error Message

                }
            }
        }
    }

    private fun changeWeeklyCalendarLoadingVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.apply {
                cpiWeeklyCalendar.visibility = View.VISIBLE
                tvWeeklyCalendarCpiLabel.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                cpiWeeklyCalendar.visibility = View.GONE
                tvWeeklyCalendarCpiLabel.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_take_stress_test -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStressTestFragment())
            }
            R.id.btn_notification -> {
                viewModel.signOut()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }
}