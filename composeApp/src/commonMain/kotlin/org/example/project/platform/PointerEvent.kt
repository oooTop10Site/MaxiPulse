package org.example.project.platform

import androidx.compose.ui.Modifier

expect fun Modifier.pointerEvent(pointerEvent: PointerEvent, action: () -> Unit): Modifier

enum class PointerEvent {
    Exit, Enter
}