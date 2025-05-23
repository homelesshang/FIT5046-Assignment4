package com.example.a5046demo.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Custom reusable text field for FitNest app.
 *
 * @param value Current input text.
 * @param onValueChange Callback to update text.
 * @param label Label text displayed in the field.
 * @param isPassword Whether to hide the input (used for passwords).
 * @param keyboardOptions Keyboard configuration.
 * @param modifier Optional layout modifier.
 */
@Composable
fun FitNestTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
        )
    )
}

/**
 * Primary filled button used across FitNest app.
 *
 * @param onClick Action triggered on button press.
 * @param text Text label to display on the button.
 * @param modifier Optional layout modifier.
 * @param enabled Whether the button is clickable.
 */
@Composable
fun FitNestButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = text)
    }
}

/**
 * Outlined-style button with optional custom content.
 *
 * @param onClick Action triggered on button press.
 * @param text Default text label (can be overridden in content).
 * @param modifier Optional layout modifier.
 * @param enabled Whether the button is clickable.
 * @param content Custom content inside the button (icon + text, etc.)
 */
@Composable
fun FitNestOutlinedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {
        Text(text = text)
    }
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        content()
    }
}

/**
 * Top app bar with centered title and optional back navigation button.
 *
 * @param title Title text displayed in the center.
 * @param showBackButton Whether to show the back arrow icon.
 * @param onBackClick Callback triggered when back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitNestTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
