package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.delivaroos.navigation.NavScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivaroos.viewmodels.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delivaroos.ui.components.ScreenLayout
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import com.example.delivaroos.ui.preview.PreviewUserViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    ScreenLayout(
        title = "Create Account",
        navController = navController,
        showBackButton = true
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var email by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var seatNo by remember { mutableStateOf("") }
            var floorNo by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf("") }
            var isLoading by remember { mutableStateOf(false) }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = seatNo,
                onValueChange = { seatNo = it },
                label = { Text("Seat Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = floorNo,
                onValueChange = { floorNo = it },
                label = { Text("Floor Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    isLoading = true
                    errorMessage = ""
                    when {
                        name.isEmpty() -> {
                            errorMessage = "Please enter your name"
                            isLoading = false
                        }
                        !email.contains("@") -> {
                            errorMessage = "Please enter a valid email"
                            isLoading = false
                        }
                        seatNo.isEmpty() -> {
                            errorMessage = "Please enter your seat number"
                            isLoading = false
                        }
                        floorNo.isEmpty() -> {
                            errorMessage = "Please enter your floor number"
                            isLoading = false
                        }
                        password.length < 6 -> {
                            errorMessage = "Password must be at least 6 characters"
                            isLoading = false
                        }
                        password != confirmPassword -> {
                            errorMessage = "Passwords do not match"
                            isLoading = false
                        }
                        else -> {
                            viewModel.register(email, password, name, seatNo, floorNo)
                            navController.navigate(NavScreen.Home.route) {
                                popUpTo(NavScreen.Register.route) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty() && 
                         confirmPassword.isNotEmpty() && name.isNotEmpty() && 
                         seatNo.isNotEmpty() && floorNo.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colors.onPrimary
                    )
                } else {
                    Text("Register")
                }
            }

            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("Already have an account? Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val context = LocalContext.current
    DelivaroosTheme {
        RegisterScreen(
            navController = rememberNavController(),
            viewModel = PreviewUserViewModel(context.applicationContext as Application)
        )
    }
}

private fun previewUserViewModel(): UserViewModel {
    return object : UserViewModel(Application()) {
        init {
            _userName.value = "John Doe"
            _userEmail.value = "john@example.com"
            _isLoggedIn.value = true
        }
    }
} 