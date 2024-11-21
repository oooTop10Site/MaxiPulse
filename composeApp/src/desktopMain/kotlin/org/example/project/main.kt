package org.example.project

import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.example.project.di.KoinInjector
import org.example.project.di.platformModule
import org.example.project.screens.root.RootApp
import org.koin.core.component.KoinComponent
import java.awt.Dimension

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MaxiPuls",
        state = rememberWindowState(
            size = DpSize(800.dp, 600.dp)
        )
    ) {
        window.minimumSize = Dimension(450, 750)
        KoinInjector.koin.loadModules(listOf(platformModule))
        RootApp()
    }
}