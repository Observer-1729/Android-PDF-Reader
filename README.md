# Android PDF Reader

A lightweight Android PDF reader built with **Kotlin** and **Jetpack Compose**, designed for simple and distraction-free PDF viewing.

The app integrates with Android's document-opening system and provides smooth PDF rendering while also supporting password-protected PDF documents.

## Features

- 📄 Open PDF documents directly through Android
- 🔐 Support for password-protected PDFs
- 🔑 Interactive password prompt for encrypted documents
- 📖 Smooth vertical PDF scrolling
- 🔍 Double-tap zoom
- 📑 Page number tracking
- ↔️ Page snapping and fling support
- 🎨 Simple, minimal interface
- 📱 Built specifically for Android

## Screenshots

### PDF Viewer

![PDF Viewer](pdfreader.png)

## Password-Protected PDFs

The application detects password-protected PDF documents and prompts the user for the required password.

The password is passed directly to the PDF rendering engine and the document is opened only when the supplied password is valid.

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Android SDK**
- **AndroidPdfViewer**
- **PDFium**
