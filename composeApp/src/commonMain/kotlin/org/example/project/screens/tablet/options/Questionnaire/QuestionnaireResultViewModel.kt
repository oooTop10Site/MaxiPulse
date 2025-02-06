package org.example.project.screens.tablet.options.Questionnaire

import kotlinx.datetime.LocalDate
import org.example.project.domain.model.questionnaire.Questionnaire
import org.example.project.domain.model.questionnaire.SportsmanQuestionnaireUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class QuestionnaireResultViewModel :
    BaseScreenModel<QuestionnaireResultState, QuestionnaireResultEvent>(
        QuestionnaireResultState.InitState
    ) {

    fun changeGroup(value: String) = intent {
        reduce {
            state.copy(selectGroup = value)
        }
    }

    fun changeSportsman(value: SportsmanQuestionnaireUI?) = intent {
        value?.let {
            reduce {
                state.copy(
                    selectSportsman = value
                )
            }
        }
    }

    fun changeDate(localDate: LocalDate) = intent {
        reduce {
            state.copy(
                date = localDate
            )
        }
    }

    fun changeType(value: Questionnaire) = intent {
        reduce {
            state.copy(
                currentType = value
            )
        }
    }

}