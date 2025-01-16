package org.example.project.screens.tablet.tests.shuttleRun

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.ext.toShuttleRunTestResult
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.ScanBluetoothSensorsManager
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.collections.map
import kotlin.collections.plus

internal class ShuttleRunViewModel :
    BaseScreenModel<ShuttleRunState, ShuttleRunEvent>(ShuttleRunState.InitState) {

    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()

    override fun onDispose() {
        super.onDispose()
        scanBluetoothSensorsManager.stopScan { }
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
                isStart = true,
                users = state.users.map {
                    it.copy(
                        sportsmanSensorUI = it.sportsmanSensorUI.copy(
                            sensor = it.sportsmanSensorUI.sensor?.copy(heartRate = emptyList())
                        )
                    )
                }
            )
        }
    }

    fun loadSportsman(sportsmans: List<SportsmanSensorUI>) = intent {
        reduce {
            state.copy(
                users = sportsmans.map {
                    TestSportsmanUI(
                        sportsmanSensorUI = it
                    )
                }
            )
        }
    }

    fun incrementTime() = intent {
        reduce {
            state.copy(
                time = state.time + 1
            )
        }
    }


    fun stop() = intent {
        reduce {
            state.copy(
                isStart = false
            )
        }
        postSideEffect(ShuttleRunEvent.StopShuttleRun(
            state.users.map {
               it.toShuttleRunTestResult()
            }
        ))
    }

}