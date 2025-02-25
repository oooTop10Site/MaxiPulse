package org.example.project.platform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.pointerEvent(pointerEvent: PointerEvent, action: () -> Unit): Modifier {
    val event = when (pointerEvent) {
        PointerEvent.Exit -> PointerEventType.Exit
        PointerEvent.Enter -> PointerEventType.Enter
        else -> null
    }
    return event?.let {
        this.onPointerEvent(
            eventType = event,
            onEvent = {
                action()
            }
        )
    } ?: this
}
