package org.example.project.domain.model.widget

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class WidgetUI(
    val id: String,
    val title: StringResource,
    val size: WidgetSize,
    val icon: DrawableResource,
    val background: DrawableResource,
    val event: WidgetUIEvent
) {
}

sealed class WidgetUIEvent() {
    object AppMiniPulse: WidgetUIEvent()
}