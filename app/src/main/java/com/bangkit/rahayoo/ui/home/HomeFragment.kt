package com.bangkit.rahayoo.ui.home

import android.os.Bundle
import android.util.Log
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
import com.bangkit.rahayoo.util.toEmoteValue
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class HomeFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val TAG = "HomeFragment"
    }

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
        binding.cvUpdateDataNow.setOnClickListener(this)
        binding.btnSeeMorePerformanceOverview.setOnClickListener(this)

        viewModel.user.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // Show Loading Progress
                }

                is UiState.Success -> {

                    val userData = uiState.data

                    if (userData.age != null) {
                        binding.cvUpdateDataNow.visibility = View.GONE
                    }

                    binding.apply {
                        Glide.with(requireContext())
                            .load(userData.avatarUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_logo_non_transparent)
                            .into(ivProfile)

                        tvProfileName.text = userData.name
                    }

                    if (userData.departmentId != null) {
                        binding.cpiPerformanceOverview.progress = 50
                        binding.tvProgressPerformanceOverviewSummary.text = "50%"
                        binding.tvPerformanceDone.text = "Done (50%)"
                        binding.tvPerformanceTodo.text = "Todo (50%)"
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

                    Log.d(TAG, "onViewCreated: $userStressLevelData")
                    binding.apply {
                        tvPercentageStressWeek.text = getString(R.string.stress_level_weekly_percentage, userStressLevelData.weeklyStressAvg ?: 0f)
                        tvEmoteStressWeek.text = userStressLevelData.weeklyMood?.toEmoteValue()
                        for (i in 0 until userStressLevelData.weeklyMoodPerDay.size) {
                            val data = userStressLevelData.weeklyMoodPerDay[i]
                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            val date = inputFormat.parse(data.date)

                            val outputFormatDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
                            val dayOfWeek = date?.let { outputFormatDayOfWeek.format(it) }
                            val outputFormatDay = SimpleDateFormat("dd", Locale.getDefault())
                            val day = date?.let { outputFormatDay.format(it) }


                            when (i) {
                                0 -> {
                                    tvDayOneDate.text = String.format("%s\n%s", day, dayOfWeek?.take(3))
                                    tvDayOneMood.text = data.mood.toEmoteValue()
                                }
                                1 -> {
                                    tvDayTwoDate.text = String.format("%s\n%s", day, dayOfWeek?.take(3))
                                    tvDayTwoMood.text = data.mood.toEmoteValue()
                                }
                                2 -> {
                                    tvDayThreeDate.text = String.format("%s\n%s", day, dayOfWeek?.take(3))
                                    tvDayThreeMood.text = data.mood.toEmoteValue()
                                }
                                3 -> {
                                    tvDayFourDate.text = String.format("%s\n%s", day, dayOfWeek?.take(3))
                                    tvDayFourMood.text = data.mood.toEmoteValue()
                                }
                                4 -> {
                                    tvDayFiveDate.text = String.format("%s\n%s", day, dayOfWeek?.take(3))
                                    tvDayFiveMood.text = data.mood.toEmoteValue()
                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    // Show Error Message

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserData()
        viewModel.getUserWeeklyCalendar()
    }

    private fun changeWeeklyCalendarLoadingVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.apply {
                cpiWeeklyCalendar.visibility = View.VISIBLE
                tvWeeklyCalendarCpiLabel.visibility = View.VISIBLE
                cvWeekSummary.visibility = View.GONE
                containerDayOne.visibility = View.GONE
                containerDayTwo.visibility = View.GONE
                containerDayThree.visibility = View.GONE
                containerDayFour.visibility = View.GONE
                containerDayFive.visibility = View.GONE
            }
        } else {
            binding.apply {
                cpiWeeklyCalendar.visibility = View.GONE
                tvWeeklyCalendarCpiLabel.visibility = View.GONE
                cvWeekSummary.visibility = View.VISIBLE
                containerDayOne.visibility = View.VISIBLE
                containerDayTwo.visibility = View.VISIBLE
                containerDayThree.visibility = View.VISIBLE
                containerDayFour.visibility = View.VISIBLE
                containerDayFive.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_take_stress_test -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStressTestFragment())
            }
            R.id.cv_update_data_now -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEditProfileFragment())
            }
            R.id.btn_see_more_performance_overview -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPerformanceDetailFragment())
            }
        }
    }
}