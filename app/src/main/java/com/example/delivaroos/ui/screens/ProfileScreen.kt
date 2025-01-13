package com.example.delivaroos.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.delivaroos.navigation.NavScreen
import com.example.delivaroos.ui.components.ScreenLayout
import com.example.delivaroos.viewmodels.UserViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import com.example.delivaroos.ui.preview.PreviewUserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    ScreenLayout(
        title = "Profile"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Existing profile content
        }
    }
}

@Composable
private fun ProfileOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val context = LocalContext.current
    DelivaroosTheme {
        ProfileScreen(
            navController = rememberNavController(),
            userViewModel = PreviewUserViewModel(context.applicationContext as Application)
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