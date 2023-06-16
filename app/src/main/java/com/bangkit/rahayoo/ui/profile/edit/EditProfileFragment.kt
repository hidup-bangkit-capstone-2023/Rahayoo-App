package com.bangkit.rahayoo.ui.profile.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.body.UserBody
import com.bangkit.rahayoo.databinding.FragmentEditProfileBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class EditProfileFragment : Fragment() {
    
    companion object {
        private const val TAG = "EditProfileFragment"
    }

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditProfileViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(requireContext()))
    }

    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDateOfBirth.setOnClickListener {
            Log.d(TAG, "onViewCreated: Pressed")
            val today = MaterialDatePicker.todayInUtcMilliseconds()

            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setEnd(today)

            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_date_of_birth))
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            picker.show(parentFragmentManager, TAG)

            picker.addOnPositiveButtonClickListener {
                calendar.timeInMillis = it
                val format = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate: String = format.format(calendar.time)

                binding.etDateOfBirth.setText(formattedDate)
            }
        }

        binding.etDepartment.setOnClickListener {
            binding.etDepartment.setText(getString(R.string.dummy_dept_name))
        }

        viewModel.userData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // Show Loading Indicator
                }

                is UiState.Success -> {
                    val userData = uiState.data

                    binding.btnSubmitEditProfile.isClickable = true
                    binding.etName.setText(userData.name)
                    binding.etAddress.setText(userData.address)
                    binding.etJobTitle.setText(userData.jobTitle)

                    if (userData.dateOfBirth != null) {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val date = inputFormat.parse(userData.dateOfBirth)
                        binding.etDateOfBirth.setText(outputFormat.format(date ?: ""))
                    }

                    Glide.with(requireContext())
                        .load(userData.avatarUrl)
                        .circleCrop()
                        .placeholder(R.drawable.ic_logo_non_transparent)
                        .into(binding.ivProfile)

                    binding.btnSubmitEditProfile.setOnClickListener {
                        val userBody = UserBody(
                            binding.etName.text.toString(),
                            binding.etAddress.text.toString(),
                            binding.etDateOfBirth.text.toString(),
                            getAge(
                                year = calendar.get(Calendar.YEAR),
                                month = calendar.get(Calendar.MONTH),
                                day = calendar.get(Calendar.DAY_OF_MONTH)
                            ),
                            binding.etJobTitle.text.toString(),
                            binding.etDepartment.text.toString(),
                            userData.email
                            )

                        viewModel.editProfile(userBody)
                    }
                }

                is UiState.Error -> {
                    // Show Error Message
                }
            }
        }

        viewModel.updateStatus.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Snackbar.make(
                        binding.root, getString(R.string.updating_profie), Snackbar.LENGTH_SHORT
                    ).show()
                }

                is UiState.Success -> {
                    Snackbar.make(
                        binding.root, uiState.data.message, Snackbar.LENGTH_SHORT
                    ).show()

                    findNavController().navigateUp()
                }

                is UiState.Error -> {
                    Snackbar.make(
                        binding.root, uiState.error, Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun getAge(year: Int, month: Int, day: Int): Int {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }

        return age
    }
}