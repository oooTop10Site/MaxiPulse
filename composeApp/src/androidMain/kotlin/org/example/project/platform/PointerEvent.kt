package org.example.project.platform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.pointerEvent(pointerEvent: PointerEvent, action: () -> Unit): Modifier =
    this
