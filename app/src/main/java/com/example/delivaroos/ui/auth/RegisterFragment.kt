package com.example.delivaroos.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.delivaroos.R
import com.example.delivaroos.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            if (validateInputs(name, email, password, confirmPassword)) {
                findNavController().navigate(R.id.action_registerFragment_to_navigation_home)
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        when {
            name.isEmpty() -> {
                showError("Please enter your name")
                return false
            }
            email.isEmpty() -> {
                showError("Please enter your email")
                return false
            }
            password.isEmpty() -> {
                showError("Please enter a password")
                return false
            }
            password != confirmPassword -> {
                showError("Passwords do not match")
                return false
            }
        }
        return true
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 