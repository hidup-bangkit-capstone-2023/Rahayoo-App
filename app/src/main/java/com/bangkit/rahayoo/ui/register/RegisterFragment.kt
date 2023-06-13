package com.bangkit.rahayoo.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.response.MessageResponseWithUserId
import com.bangkit.rahayoo.databinding.FragmentRegisterBinding
import com.bangkit.rahayoo.di.Injection
import com.bangkit.rahayoo.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener(this)
        binding.tvHaveAccount.setOnClickListener(this)

        viewModel.authResult.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    when (it.data) {
                        is com.google.firebase.auth.AuthResult -> {
                            viewModel.registerServer(binding.etName.text.toString(), binding.etEmail.text.toString())
                        }
                        is MessageResponseWithUserId -> {
                            binding.progressIndicator.visibility = View.GONE

                            Snackbar.make(
                                binding.root,
                                it.data.message,
                                Snackbar.LENGTH_SHORT
                            ).show()

                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                        }
                    }
                }
                is UiState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    // Show error snackbar
                    Snackbar.make(
                        binding.root,
                        it.error,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_register -> {
                viewModel.registerFirebase(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
            R.id.tv_have_account -> {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        }
    }
}