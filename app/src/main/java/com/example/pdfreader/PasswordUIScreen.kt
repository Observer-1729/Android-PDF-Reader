package com.example.pdfreader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PdfPasswordDialog(
    password: String,
    onPasswordChange: (String) -> Unit,
    onUnlock: () -> Unit,
    onCancel: () -> Unit,
    error: String? = null
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    AlertDialog(

        onDismissRequest = {
            keyboardController?.hide()
        },

        title = {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )

                Text(
                    text = "Unlock PDF",
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        },

        text = {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = password,

                    onValueChange = {
                        onPasswordChange(it)
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Password")
                    },

                    placeholder = {
                        Text("Enter password")
                    },

                    singleLine = true,

                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

                    trailingIcon = {

                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {

                            Icon(

                                imageVector =
                                    if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,

                                contentDescription =
                                    if (passwordVisible)
                                        "Hide password"
                                    else
                                        "Show password"
                            )
                        }
                    },

                    isError = error != null
                )

                if (error != null) {

                    Text(
                        text = error,
                        color = Color(0xFFD32F2F),
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                top = 6.dp
                            )
                    )
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    keyboardController?.hide()
                    onUnlock()
                },

                enabled = password.isNotBlank()
            ) {

                Text(
                    text = "UNLOCK",
                    color = Color(0xFF6750A4)
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = {

                    keyboardController?.hide()
                    onCancel()
                }
            ) {

                Text(
                    text = "CANCEL",
                    color = Color(0xFFD32F2F)
                )
            }
        }
    )
}