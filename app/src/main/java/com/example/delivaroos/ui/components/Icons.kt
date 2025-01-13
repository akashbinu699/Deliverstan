package com.example.delivaroos.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CheckCircleIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFF4CAF50) // Same as green_success color
) {
    Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = "Success",
        modifier = modifier,
        tint = tint
    )
} 