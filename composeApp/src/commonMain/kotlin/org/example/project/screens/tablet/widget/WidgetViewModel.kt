package org.example.project.screens.tablet.widget

import org.example.project.domain.model.widget.WidgetUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class WidgetViewModel : BaseScreenModel<WidgetState, WidgetEvent>(WidgetState.InitState) {

    fun changePosition(moving: WidgetUI, stating: WidgetUI) = intent() {
        reduce {
            state.copy(widgets = state.widgets.map {
                when (it.id) {
                    moving.id -> stating
                    stating.id -> moving
                    else -> it
                }
            })
        }
    }

    fun changeSelect(widgetUI: WidgetUI) = intent {
        reduce {
            state.copy(
                selected = if (widgetUI in state.selected) state.selected - widgetUI else state.selected + widgetUI
            )
        }
    }

    fun changeIsSelectAll() = intent {
        reduce {
            state.copy(
                selected = if (!state.isSelectAll) state.widgets else state.selected,
                isSelectAll = !state.isSelectAll
            )
        }
    }

    fun changeIsEditing() = intent {
        reduce {
            state.copy(
                isEditing = !state.isEditing
            )
        }
    }

}