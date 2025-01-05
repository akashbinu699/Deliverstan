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
            val floor = binding.editTextFloor.text.toString()
            val seatNo = binding.editTextSeatNo.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (validateInputs(name, email, floor, seatNo, password)) {
                findNavController().navigate(R.id.action_registerFragment_to_navigation_home)
            }
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        floor: String,
        seatNo: String,
        password: String
    ): Boolean {
        var isValid = true

        if (name.isBlank()) {
            binding.editTextName.error = "Name is required"
            isValid = false
        }

        if (email.isBlank()) {
            binding.editTextEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Invalid email format"
            isValid = false
        }

        if (floor.isBlank()) {
            binding.editTextFloor.error = "Floor is required"
            isValid = false
        }

        if (seatNo.isBlank()) {
            binding.editTextSeatNo.error = "Seat number is required"
            isValid = false
        }

        if (password.length < 6) {
            binding.editTextPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 