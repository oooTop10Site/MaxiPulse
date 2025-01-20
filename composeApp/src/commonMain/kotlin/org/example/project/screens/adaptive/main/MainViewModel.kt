package org.example.project.screens.adaptive.main

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestUI
import org.example.project.domain.repository.GamerRepository
import org.example.project.ext.toSensorUI
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.utils.Constants
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.InitState) {
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()
    val observerManager: MessageObserverManager by inject()
    val sportsmanRepository: GamerRepository by inject()

    override fun onDispose() {
        super.onDispose()
        scanBluetoothSensorsManager.stopScan { }
    }

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    var job: Job? = null
    fun search(value: String) = intent {
        job?.cancel()
        job = screenModelScope.launch {
            delay(Constants.Debounce)
            val queryWords = value.trim().lowercase().split(" ") // Разбиваем запрос на слова
            reduce {
                state.copy(
                    filterSportsmans = state.sportsmans.filter { sportman ->
                        val fio = sportman.fio.lowercase() // Приводим ФИО спортсмена к нижнему регистру
                        queryWords.all { word -> fio.contains(word) } // Проверяем, что все слова из запроса есть в ФИО
                    }
                )
            }
        }
    }

    fun loadSportsman() = intent {
        reduce {
            state.copy(
                search = ""
            )
        }
        launchOperation(
            operation = {
                sportsmanRepository.getGamers()
            },
            success = {
                val sportsmans = it.map { it.toSensorUI() }
                reduceLocal {
                    state.copy(
                        sportsmans = sportsmans,
                        filterSportsmans = sportsmans
                    )
                }
            }
        )
    }

    fun addSensor(sensorUI: SensorUI) = intent {
        if (sensorUI.sensorId !in state.sensors.orEmpty().map { it.sensorId }) {
            reduce {
                state.copy(
                    sensors = state.sensors.orEmpty() + sensorUI
                )
            }
        }
    }

    fun changeIsStartTraining() = intent {
        reduce {
            state.copy(
                isStartTraining = !state.isStartTraining
            )
        }
    }

    fun changeAlertDialog(alertDialog: MainAlertDialog?) = intent {
        if (alertDialog !is MainAlertDialog.SelectSensor) {
            scanBluetoothSensorsManager.stopScan { }
        }
        reduce {
            state.copy(
                alertDialog = alertDialog,
            )
        }
    }

    fun startTraining(testUI: TestUI?, checkCond: Boolean = true) = intent {
        val alertDialog = when {
            state.sportsmans.find { it.sensor == null && it.id in state.selectSportsmans } != null -> MainAlertDialog.PlayerWithNoActiveSensor
            else -> null
        }
        reduce {
            state.copy(
                alertDialog = alertDialog
            )
        }
        if (alertDialog == null || !checkCond) {
            reduce {
                state.copy(
                    alertDialog = null
                )
            }
            when (testUI) {
                is TestUI.ReadiesForUpload -> {
                    postSideEffect(MainEvent.ReadiesForUpload(state.sportsmans.filter { it.id in state.selectSportsmans }))
                }

                is TestUI.ShuttleRun -> {
                    postSideEffect(MainEvent.ShuttleRun(state.sportsmans.filter { it.id in state.selectSportsmans }))
                }

                null -> {
                    postSideEffect(MainEvent.Training(sportsmans = state.sportsmans.filter { it.id in state.selectSportsmans }))
                }
            }
        }
    }

    fun changeSensorValidation(sensorUI: SensorUI, sportsmanId: String) = intent {
        val userAlreadyHasSensor =
            state.sportsmans.find { it.sensor?.sensorId == sensorUI.sensorId && it.id != sportsmanId }
        userAlreadyHasSensor?.let {
            reduce {
                state.copy(
                    alertDialog = MainAlertDialog.SensorAlreadyAssigned(
                        sensorUI = sensorUI,
                        sportsmanId = sportsmanId
                    ),
                )
            }
            return@intent
        }
        changeSensor(sensorUI, sportsmanId)
    }

    fun changeSensor(sensorUI: SensorUI, sportsmanId: String) = intent {
        reduce {
            state.copy(
                filterSportsmans = state.filterSportsmans.map {
                    when {
                        it.id == sportsmanId -> it.copy(
                            sensor = sensorUI
                        )

                        sensorUI.sensorId == it.sensor?.sensorId -> it.copy(sensor = null)

                        else -> it
                    }
                },
                sportsmans = state.sportsmans.map {
                    when {
                        it.id == sportsmanId -> it.copy(
                            sensor = sensorUI
                        )

                        sensorUI.sensorId == it.sensor?.sensorId -> it.copy(sensor = null)

                        else -> it
                    }
                },
                alertDialog = null
            )
        }
    }

    fun changeSelect(sportsmanSensorUI: SportsmanSensorUI) = intent {
        reduce {
            state.copy(
                selectSportsmans = if (sportsmanSensorUI.id in state.selectSportsmans)
                    state.selectSportsmans - sportsmanSensorUI.id
                else state.selectSportsmans + sportsmanSensorUI.id
            )
        }
    }

    fun changeIsActiveSensor() = intent {
        val newValue = !state.isActiveSensor
        reduce {
            state.copy(
                isActiveSensor = newValue,
                selectSportsmans = (if (newValue)
                    state.sportsmans.filter { it.sensor != null }.map { it.id }
                else state.selectSportsmans)
            )
        }
    }


    //mobile

    fun openMenu() = intent {
        observerManager.openMobileMenu()
    }
}