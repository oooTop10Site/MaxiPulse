@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.tablet.sportsman.edit

import kotlinx.datetime.LocalDate
import maxipuls.composeapp.generated.resources.Res
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
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.rank.RankUI
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.trainingStage.TrainingStageUI
import org.example.project.domain.repository.GroupRepository
import org.example.project.ext.calculateAge


internal class SportsmanEditViewModel : BaseScreenModel<SportsmanEditState, SportsmanEditEvent>(
    SportsmanEditState.InitState
) {
    private val gamerRepository: GamerRepository by inject()
    private val groupRepository: GroupRepository by inject()
    private val observerManager: MessageObserverManager by inject()
    val imagePermissionsService: PermissionsService by inject()

    fun deleteSportsmanDialog() = intent {
        reduce {
            state.copy(
                deleteSportsmanDialog = !state.deleteSportsmanDialog
            )
        }
    }

    fun loadSportsman(id: String?) = intent {
        if (!id.isNullOrBlank()) {
            launchOperation(
                operation = {
                    gamerRepository.getGamer(gamerId = id)
                },
                success = {
                    loadTrainingStages(it.gameTypeId)
                    loadRanks(it.gameTypeId)
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


    fun changeSensorAlertDialog() = intent {
        reduce {
            state.copy(
                sensorAlertDialog = !state.sensorAlertDialog,
            )
        }
    }

    fun deleteSportsman(gamerId: String?) = intent {
        gamerId?.let {
            launchOperation(operation = {
                gamerRepository.deleteGamer(gamerId)
            }, success = {
                deleteSportsmanDialog()
                postSideEffectLocal(SportsmanEditEvent.Delete)
            })

        }
    }

    fun changeSensor(it: SensorUI) = intent {
        reduce {
            state.copy(
                sensorAlertDialog = false,
                sensorUI = it
            )
        }
    }

    fun addSensor(sensor: SensorUI) = intent {
        if (sensor.sensorId !in state.sensors.orEmpty().map { it.sensorId }) {
            reduce {
                state.copy(
                    sensors = state.sensors.orEmpty() + sensor
                )
            }
        }
    }

    fun changeSettingChssDialog() = intent {
        reduce {
            state.copy(
                settingChssDialog = !state.settingChssDialog
            )
        }
    }

    fun loadGameTypes() = intent {
        launchOperation(
            operation = {
                gamerRepository.getGameTypes()
            },
            success = {
                reduceLocal {
                    state.copy(
                        gameTypes = it
                    )
                }
            }
        )
    }

    fun loadRanks(gameTypeId: String = state.sportsmanUI.gameTypeId) = intent {
        launchOperation(
            operation = {
                gamerRepository.getRank(gameTypeId)
            },
            success = {
                reduceLocal {
                    state.copy(ranks = it)
                }
            }
        )
    }

    fun loadTrainingStages(gameTypeId: String = state.sportsmanUI.gameTypeId) = intent {
        launchOperation(
            operation = {
                gamerRepository.getTrainingStage(gameTypeId)
            },
            success = {
                reduceLocal {
                    state.copy(trainingStages = it)
                }
            }
        )
    }

    fun loadGroups() = intent {
        launchOperation(
            operation = {
                groupRepository.getGroups()
            },
            success = {
                reduceLocal {
                    state.copy(
                        groups = it
                    )
                }
            }
        )
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
            val birthday = LocalDate(year = year, monthNumber = month, dayOfMonth = day)
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    age = birthday.calculateAge(),
                    birthDay = birthday
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

    fun changeSex(isMale: Boolean) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(isMale = isMale)
            )
        }
    }

    fun changeSport(gameTypeUI: GameTypeUI) = intent {
        if (gameTypeUI.id != state.sportsmanUI.gameTypeId) {
            println("change gameTypeUI - $gameTypeUI")
            reduce {
                state.copy(
                    sportsmanUI = state.sportsmanUI.copy(
                        gameTypeId = gameTypeUI.id, trainigStageId = "", rankId = ""
                    )
                )
            }
        }
        loadRanks(gameTypeUI.id)
        loadTrainingStages(gameTypeUI.id)
    }


    fun changeGroup(value: GroupUI) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    groupId = value.id
                )
            )
        }
    }

    fun changeRank(value: RankUI) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    rankId = value.id
                )
            )
        }
    }

    fun changeTrainingStage(value: TrainingStageUI) = intent {
        reduce {
            state.copy(
                sportsmanUI = state.sportsmanUI.copy(
                    trainigStageId = value.id
                )
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