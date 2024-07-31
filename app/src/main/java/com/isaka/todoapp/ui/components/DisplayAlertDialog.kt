package com.isaka.todoapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = message)
            },
            onDismissRequest = { onCancelClicked() },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmClicked()
                        onCancelClicked()
                    }
                ) {
                    Text(text = "YES")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { onCancelClicked() }
                ) {
                    Text(text = "CANCEL")
                }
            }
        )
    }
}
