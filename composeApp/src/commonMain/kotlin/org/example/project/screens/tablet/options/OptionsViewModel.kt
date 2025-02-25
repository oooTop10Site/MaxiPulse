package org.example.project.screens.tablet.options

import org.example.project.domain.model.option.Option
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class OptionsViewModel :
    BaseScreenModel<OptionsState, OptionsEvent>(OptionsState.InitState) {

    fun changeOption(option: Option) = intent {
        reduce {
            state.copy(
                selectOption = option
            )
        }
    }


}