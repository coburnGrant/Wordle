package com.example.wordle.ui.helpers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertView(title: String, body: String, buttonLabel: String, onClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onClick() },
        title = {
            Text(title)
        },
        text = {
            Text(body)
        },
        confirmButton = {
            TextButton(
                onClick = { onClick() },
            ) {
                Text(buttonLabel)
            }
        }
    )
}