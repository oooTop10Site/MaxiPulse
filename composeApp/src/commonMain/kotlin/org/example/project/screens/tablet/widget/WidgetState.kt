package org.example.project.screens.tablet.widget

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.control_analize
import maxipuls.composeapp.generated.resources.control_and_analize
import maxipuls.composeapp.generated.resources.mark_function_state
import maxipuls.composeapp.generated.resources.mark_function_state_by_cardio_beat
import maxipuls.composeapp.generated.resources.minipulse_app
import maxipuls.composeapp.generated.resources.mobile_widget
import maxipuls.composeapp.generated.resources.purple_brush
import maxipuls.composeapp.generated.resources.test_4x54
import maxipuls.composeapp.generated.resources.test_5x54
import maxipuls.composeapp.generated.resources.yyir
import maxipuls.composeapp.generated.resources.yyir1
import org.example.project.domain.model.widget.WidgetSize
import org.example.project.domain.model.widget.WidgetUI
import org.example.project.domain.model.widget.WidgetUIEvent
import org.example.project.platform.randomUUID

data class WidgetState(
    val widgets: List<WidgetUI>,
    val isEditing: Boolean,
    val selected: List<WidgetUI>,
    val isSelectAll: Boolean,
) {
    companion object {
        val InitState = WidgetState(
            widgets = listOf(
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.mobile_widget,
                    size = WidgetSize.Small,
                    event = WidgetUIEvent.AppMiniPulse,
                    title = Res.string.minipulse_app
                ),
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.control_analize,
                    size = WidgetSize.Small,
                    event = WidgetUIEvent.Nothing,
                    title = Res.string.control_and_analize
                ),
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.test_5x54,
                    size = WidgetSize.Small,
                    event = WidgetUIEvent.Nothing,
                    title = Res.string.test_4x54
                ),
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.mark_function_state,
                    size = WidgetSize.Small,
                    event = WidgetUIEvent.Nothing,
                    title = Res.string.mark_function_state_by_cardio_beat
                ),
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.yyir,
                    size = WidgetSize.Small,
                    event = WidgetUIEvent.Nothing,
                    title = Res.string.yyir1
                ),
                WidgetUI(
                    id = randomUUID(),
                    background = Res.drawable.purple_brush,
                    icon = Res.drawable.control_analize,
                    size = WidgetSize.Large,
                    event = WidgetUIEvent.Nothing,
                    title = Res.string.control_and_analize
                ),
            ),
            false,
            emptyList(),
            false
        )
    }
}