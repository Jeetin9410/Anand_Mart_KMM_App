package org.example.project.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography

@Composable
fun showDialogPopUp(
    title: String,
    message: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    dismissVisibility: Boolean = true,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.small,
        title = {
            Text(title, style = appTypography().h6)
        },
        text = {
            Text(message, style = appTypography().body1)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText, style = appTypography().body1, color = AppColors.warning, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            if (dismissVisibility)
                TextButton(onClick = onDismiss) {
                    Text(cancelText, style = appTypography().body1, color = AppColors.warning, fontWeight = FontWeight.Bold)
                }
        }
    )
}