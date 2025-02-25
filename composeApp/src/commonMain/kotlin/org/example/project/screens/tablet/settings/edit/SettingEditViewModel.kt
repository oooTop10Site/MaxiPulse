package org.example.project.screens.tablet.settings.edit

import org.example.project.platform.BaseScreenModel
import org.example.project.platform.permission.service.PermissionsService
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SettingEditViewModel: BaseScreenModel<SettingEditState, SettingEditEvent>(SettingEditState.InitState) {
    val imagePermissionsService: PermissionsService by inject()
    fun changeLogo(value: String) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    avatar = value
                )
            )
        }
    }

    fun changeTitle(value: String) = blockingIntent {
        reduce {
            state.copy(
                title = value
            )
        }
    }

    fun changeDescription(value: String) = blockingIntent {
        reduce {
            state.copy(
                desc = value
            )
        }
    }

    fun changeCity(value: String) = blockingIntent {
        reduce {
            state.copy(
                city = value
            )
        }
    }

}