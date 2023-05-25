package com.bangkit.rahayoo.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.data.model.AuthResult
import com.bangkit.rahayoo.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener(this)
        binding.tvNoAccount.setOnClickListener(this)

        viewModel.authResult.observe(viewLifecycleOwner) {
            when (it) {
                is AuthResult.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is AuthResult.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    if (it.data != null) {
                        // TODO: Navigate to Home
                        Snackbar.make(
                            binding.root,
                            "Login Success",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                is AuthResult.Error -> {
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
        when (v!!.id) {
            binding.btnLogin.id -> {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(email, password)
            }
            binding.tvNoAccount.id -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }
}