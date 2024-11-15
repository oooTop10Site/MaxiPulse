package org.example.project.platform

import androidx.compose.ui.Modifier

actual fun Modifier.pointerEvent(pointerEvent: PointerEvent, action: () -> Unit): Modifier = this