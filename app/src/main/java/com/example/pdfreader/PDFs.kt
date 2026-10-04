
package com.example.pdfreader

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfScreen(
    uri: Uri,
    onBack: () -> Unit
) {

    var currentPage by remember {
        mutableStateOf(0)
    }

    var totalPages by remember {
        mutableStateOf(0)
    }

    // Password dialog
    var showPasswordDialog by remember {
        mutableStateOf(false)
    }

    // Password entered by user
    var password by remember {
        mutableStateOf("")
    }

    // Error displayed inside password dialog
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }

    // Password currently being used by PDF viewer
    var pdfPassword by remember {
        mutableStateOf<String?>(null)
    }

    val isDarkMode =
        androidx.compose.foundation.isSystemInDarkTheme()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        key(pdfPassword) {

            AndroidView(
                modifier = Modifier.fillMaxSize(),

                factory = { context ->

                    PDFView(context, null).apply {

                        setBackgroundColor(
                            0xFCDBFF
                        )

                        val configurator = fromUri(uri)

                        // Only provide password when we have one.
                        if (pdfPassword != null) {
                            configurator.password(pdfPassword!!)
                        }

                        configurator
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .spacing(10)
                            .autoSpacing(true)
                            .pageFling(true)
                            .pageSnap(true)

                            .onPageChange { page, pageCount ->

                                currentPage = page + 1
                                totalPages = pageCount
                            }

                            .onError {


                                if (pdfPassword == null) {

                                    showPasswordDialog = true

                                    passwordError = null
                                } else {



                                    passwordError =
                                        "Incorrect password"

                                    showPasswordDialog = true
                                }
                            }

                            .load()
                    }
                }
            )
        }


        Icon(
            imageVector = Icons.Default.Close,

            contentDescription = "Close PDF",

            tint = Color.Black,

            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable {
                    onBack()
                }
                .size(22.dp)
        )


        Text(
            text = "$currentPage / $totalPages",

            color = Color.Black,

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )


        if (showPasswordDialog) {

            PdfPasswordDialog(

                password = password,

                onPasswordChange = {

                    password = it

                    passwordError = null
                },

                onUnlock = {

                    if (password.isNotBlank()) {

                        passwordError = null

                        pdfPassword = password

                        showPasswordDialog = false
                    }
                },

                onCancel = {

                    showPasswordDialog = false

                    onBack()
                },

                error = passwordError
            )
        }
    }
}