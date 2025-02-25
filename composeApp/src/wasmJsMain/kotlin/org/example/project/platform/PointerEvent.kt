package org.example.project.platform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.pointerEvent(pointerEvent: PointerEvent, action: () -> Unit): Modifier {
    return this.onPointerEvent(
        eventType = when (pointerEvent) {
            PointerEvent.Exit -> PointerEventType.Exit
            PointerEvent.Enter -> PointerEventType.Enter
            else -> PointerEventType.Enter
        },
        onEvent =  {
            action()
        }
    )
}
