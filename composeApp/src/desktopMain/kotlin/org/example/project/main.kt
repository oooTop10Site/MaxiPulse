package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.KoinInjector
import org.example.project.screens.root.RootApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MaxiPuls",
    ) {
        KoinInjector.koin
        RootApp()
    }
}