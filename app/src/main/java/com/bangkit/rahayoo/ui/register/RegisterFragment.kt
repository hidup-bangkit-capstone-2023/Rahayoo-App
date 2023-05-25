package com.bangkit.rahayoo.ui.register

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.AuthResult
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel>()

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
                is AuthResult.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is AuthResult.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    if (it.data != null) {
                        // TODO: Navigate to Insert Organization Fragment
                        val user = it.data as FirebaseUser
                        Snackbar.make(
                            binding.root,
                            "Register Success ${user.displayName}",
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
        when(v!!.id) {
            R.id.btn_register -> {
                viewModel.register(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
            R.id.tv_have_account -> {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        }
    }
}