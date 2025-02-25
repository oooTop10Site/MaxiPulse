package org.example.project.screens.tablet.tests.readiesForUpload

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.ext.toReadiesForUploadResult
import org.example.project.ext.toShuttleRunTestResult
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.ScanBluetoothSensorsManager
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.getValue

internal class ReadiesForUploadViewModel :
    BaseScreenModel<ReadiesForUploadState, ReadiesForUploadEvent>(
        ReadiesForUploadState.InitState
    ) {

    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()

    override fun onDispose() {
        super.onDispose()
        scanBluetoothSensorsManager.stopScan { }
    }

    fun stop() = intent {
        reduce {
            state.copy(
                isStart = false
            )
        }
    }

    fun changeTest() = intent {
        reduce {
            state.copy(isStart = false)
        }
        val nextText = state.tests.getOrNull(state.tests.indexOf(state.currentTest) + 1)
        if (nextText != null) {
            reduce {
                state.copy(
                    currentTest = nextText,
                    time = 10L
                )
            }
        } else {
            postSideEffect(ReadiesForUploadEvent.Result(state.users.map { it.toReadiesForUploadResult() }))
        }
    }

    fun newDataFromSportsman(sensorUI: SensorUI) = intent {
        val newSportsmans = state.users.map { sportsman ->
            if (sportsman.sportsmanSensorUI.sensor?.sensorId == sensorUI.sensorId) {
                println("Нашли нащего спорстмена")
                sportsman.copy(
                    sportsmanSensorUI = sportsman.sportsmanSensorUI.copy(
                        sensor = sensorUI.copy(
                            heartRate = sportsman.sportsmanSensorUI.sensor.heartRate + sensorUI.heartRate
                        )
                    )
                )
            } else sportsman
        }
        println("newSportsmans - $newSportsmans")
        reduce {
            state.copy(
                users = newSportsmans
            )
        }
    }


    fun start() = intent {
        reduce {
            state.copy(
                users = if (state.time == ReadiesForUploadState.InitState.time) state.users.map {
                    it.copy(
                        sportsmanSensorUI = it.sportsmanSensorUI.copy(
                            sensor = it.sportsmanSensorUI.sensor?.copy(heartRate = emptyList())
                        )
                    )
                } else state.users,
                isStart = true
            )
        }
    }

    fun decrementTime() = intent {
        val newTime = state.time - 1
        if (state.isStart) {
            reduce {
                state.copy(
                    time = newTime
                )
            }

            if (newTime == 0L) {
                changeTest()
            }
        }
    }

    fun loadSportmans(uIS: List<SportsmanSensorUI>) = intent {
        reduce {
            state.copy(
                users = uIS.map {
                    TestSportsmanUI(
                        sportsmanSensorUI = it
                    )
                }
            )
        }
    }
}