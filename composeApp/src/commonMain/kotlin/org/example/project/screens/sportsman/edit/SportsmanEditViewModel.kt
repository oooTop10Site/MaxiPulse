@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.sportsman.edit

import kotlinx.datetime.LocalDate
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.save
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.utils.orEmpty
import org.example.project.utils.toStringWithCondition
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import maxipuls.composeapp.generated.resources.success_save


internal class SportsmanEditViewModel : BaseScreenModel<SportsmanEditState, SportsmanEditEvent>(
    SportsmanEditState.InitState
) {
    private val gamerRepository: GamerRepository by inject()
    private val observerManager: MessageObserverManager by inject()
    val imagePermissionsService: PermissionsService by inject()
    fun loadSportsman(id: String?) = intent {
        if (!id.isNullOrBlank()) {
            launchOperation(
                operation = {
                    gamerRepository.getGamer(gamerId = id)
                },
                success = {
                    reduceLocal {
                        state.copy(
                            sportsmanUI = it,
                            imt = it.imt.toStringWithCondition()
                        )
                    }
                }
            )
        }
    }

    fun changeAvatar(avatar: String) = blockingIntent() {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(avatar = avatar)
            )
        }
    }

    fun changeDate(year: Int, month: Int, day: Int) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    birthDay = LocalDate(year = year, monthNumber = month, dayOfMonth = day)
                )
            )
        }

    }

    fun save() = intent {
        val successMessage = getString(Res.string.success_save)
        launchOperation(
            operation = {
                gamerRepository.editGamer(
                    gamerId = state.sportsmanUI.id.ifBlank { null },
                    state.sportsmanUI
                )
            },
            success = {
                observerManager.putMessage(successMessage)
                loadSportsman(state.sportsmanUI.id.ifBlank { null })
            }
        )
    }

    fun changeHeight(height: String) = blockingIntent {
        val newHeight = height.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(height = newHeight.orEmpty())
            )
        }
    }

    fun changeWeight(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(weight = newValue.orEmpty())
            )
        }
    }


    fun changeFirstname(value: String) = blockingIntent {
        val newValue = value.take(40).filter { it.isLetter() }
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(name = newValue.orEmpty())
            )
        }
    }

    fun changeMiddleName(value: String) = blockingIntent {
        val newValue = value.take(40).filter { it.isLetter() }
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(middleName = newValue)
            )
        }
    }

    fun changeLastname(value: String) = blockingIntent {
        val newValue = value.take(40).filter { it.isLetter() }
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(lastname = newValue.orEmpty())
            )
        }
    }

    fun changeMPK(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(mpk = newValue.orEmpty())
            )
        }
    }

    fun changeNumber(value: String) = blockingIntent {
        val newValue = value.take(50).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(number = newValue.orEmpty())
            )
        }
    }

    fun changeChssPano(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(chssPano = newValue.orEmpty())
            )
        }
    }

    fun changeChssPao(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(chssPao = newValue.orEmpty())
            )
        }
    }

    fun changeChssMax(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(chssMax = newValue.orEmpty())
            )
        }

    }

    fun changeChssResting(value: String) = blockingIntent {
        val newValue = value.take(3).filter { it.isDigit() }.toIntOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(chssResting = newValue.orEmpty())
            )
        }

    }

    fun changeImtString(value: String) = blockingIntent {
        reduce {
            state.copy(
                imt = value
            )
        }
    }

    fun changeImt(value: String = state.imt) = blockingIntent {
        val newValue = value.take(5).toDoubleOrNull()
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(imt = newValue.orEmpty()),
                imt = newValue.orEmpty().toStringWithCondition()
            )
        }
    }


}