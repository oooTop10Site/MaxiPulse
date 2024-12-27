package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.di.KoinInjector
import org.example.project.di.platformModule
import org.example.project.screens.adaptive.root.RootApp

fun MainViewController() = ComposeUIViewController {
    KoinInjector.koin.loadModules(listOf(platformModule))
    RootApp()
}