package com.example.delivaroos.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.delivaroos.R
import com.example.delivaroos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var profileImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
            binding.imageProfile.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupUserInfo()

        binding.buttonChangeProfilePicture.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun setupUserInfo() {
        binding.textName.text = "Test User"
        binding.textEmail.text = "test@test.com"
    }

    private fun setupClickListeners() {
        binding.buttonEditProfile.setOnClickListener {
            Toast.makeText(context, "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }

        binding.buttonAddress.setOnClickListener {
            Toast.makeText(context, "Delivery Address clicked", Toast.LENGTH_SHORT).show()
        }

        binding.buttonPayment.setOnClickListener {
            Toast.makeText(context, "Payment Methods clicked", Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 