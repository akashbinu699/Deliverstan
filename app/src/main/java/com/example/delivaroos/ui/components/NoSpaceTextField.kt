package com.example.delivaroos.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun NoSpaceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true
) {
    val textColor = MaterialTheme.colors.onSurface
    val labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Remove spaces from the input
            onValueChange(newValue.replace(" ", ""))
        },
        label = { Text(label, color = labelColor) },
        modifier = modifier,
        keyboardOptions = keyboardOptions.copy(
            imeAction = keyboardOptions.imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor,
            cursorColor = MaterialTheme.colors.primary,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = labelColor,
            backgroundColor = Color.Transparent
        )
    )
} 