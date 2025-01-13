package com.example.delivaroos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.delivaroos.data.UserPreferences
import kotlinx.coroutines.launch

open class UserViewModel(application: Application) : AndroidViewModel(application) {
    protected val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    protected val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    protected val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    protected val _userPhoto = MutableLiveData<String>()
    val userPhoto: LiveData<String> = _userPhoto

    private val userPreferences = UserPreferences(application)

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            userPreferences.isLoggedIn.collect { isLoggedIn ->
                _isLoggedIn.value = isLoggedIn
            }
        }
    }

    fun login(email: String, password: String): Boolean {
        return if (email == "test@test.com" && password == "password") {
            viewModelScope.launch {
                userPreferences.setLoggedIn(true)
                _isLoggedIn.value = true
                _userName.value = "John Doe"
                _userEmail.value = email
            }
            true
        } else {
            false
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUser()
            _isLoggedIn.value = false
            _userName.value = ""
            _userEmail.value = ""
            _userPhoto.value = null
        }
    }

    fun updateUserPhoto(photoUri: String) {
        _userPhoto.value = photoUri
    }

    fun register(email: String, password: String, name: String, seatNo: String, floorNo: String) {
        viewModelScope.launch {
            try {
                // For testing purposes
                if (email == "test@test.com" && password == "password") {
                    userPreferences.setLoggedIn(true)
                    _isLoggedIn.value = true
                    _userName.value = name
                    _userEmail.value = email
                    // Store additional user info
                    userPreferences.saveUserDetails(name, seatNo, floorNo)
                }
            } catch (e: Exception) {
                // Handle registration error
            }
        }
    }
} 