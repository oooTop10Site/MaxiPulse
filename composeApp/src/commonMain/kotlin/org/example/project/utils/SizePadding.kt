package org.example.project.utils

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.screens.adaptive.root.ScreenSize


@Composable
fun safeAreaHorizontal(): Dp {
    val screenSize = ScreenSize.currentOrThrow
    return when (screenSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            16.dp
        }

        WindowWidthSizeClass.Medium -> {
            20.dp
        }

        WindowWidthSizeClass.Expanded -> {
            20.dp
        }

        else -> {
            20.dp
        }
    }
}

@Composable
fun safeAreaVertical(): Dp {
    val screenSize = ScreenSize.currentOrThrow
    return when (screenSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            16.dp
        }

        WindowWidthSizeClass.Medium -> {
            20.dp
        }

        WindowWidthSizeClass.Expanded -> {
            20.dp
        }

        else -> {
            20.dp
        }
    }
}