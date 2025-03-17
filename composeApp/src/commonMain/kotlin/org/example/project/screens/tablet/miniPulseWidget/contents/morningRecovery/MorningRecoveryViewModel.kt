package org.example.project.screens.tablet.miniPulseWidget.contents.morningRecovery

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.ext.weekDates
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MorningRecoveryViewModel :
    BaseScreenModel<MorningRecoveryState, MorningRecoveryEvent>(
        MorningRecoveryState.InitState
    ) {

    fun loadUsers() = intent {
        reduce {
            state.copy(
                filterUsers = state.users.filter { it.date == state.selectDate }
            )
        }
    }

    fun incrementDate() = intent {
        val newDate = state.selectDate.plus(DatePeriod(days = 1))
        reduce {
            state.copy(
                selectDate = newDate,
                currentWeek = newDate.weekDates(),
                filterUsers = state.users.filter { it.date == newDate }
            )
        }
    }

    fun changeAlertDialog() = intent {
        reduce {
            state.copy(
                isAlertDialog = !state.isAlertDialog
            )
        }
    }

    fun changeIsExpand(selectSportsmanId: String? = null) = intent {
        reduce {
            state.copy(
                selectSportsman = if (selectSportsmanId == state.selectSportsman?.firstOrNull()?.id) null else state.users.filter { it.sportsmanId == selectSportsmanId }
            )
        }
    }

    fun decrementDate() = intent {
        val newDate = state.selectDate.minus(DatePeriod(days = 1))
        reduce {
            state.copy(
                selectDate = newDate,
                currentWeek = newDate.weekDates(),
                filterUsers = state.users.filter { it.date == newDate }
            )
        }
    }

}