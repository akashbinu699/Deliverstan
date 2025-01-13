package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.delivaroos.navigation.NavScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivaroos.viewmodels.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.delivaroos.ui.components.NoSpaceTextField
import com.example.delivaroos.ui.components.ScreenLayout
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import com.example.delivaroos.ui.preview.PreviewUserViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    ScreenLayout(
        title = "Login"
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
            var password by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf("") }

            val focusManager = LocalFocusManager.current

            fun handleLogin() {
                if (viewModel.login(email, password)) {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(NavScreen.Login.route) { inclusive = true }
                    }
                } else {
                    errorMessage = "Invalid credentials"
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            NoSpaceTextField(
                value = email,
                onValueChange = { 
                    email = it
                    errorMessage = ""
                },
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            NoSpaceTextField(
                value = password,
                onValueChange = { 
                    password = it
                    errorMessage = ""
                },
                label = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { handleLogin() }
                )
            )

            Button(
                onClick = { handleLogin() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Login")
            }

            TextButton(
                onClick = { navController.navigate(NavScreen.Register.route) }
            ) {
                Text("Don't have an account? Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    DelivaroosTheme {
        LoginScreen(
            navController = rememberNavController(),
            viewModel = PreviewUserViewModel(context.applicationContext as Application)
        )
    }
} 