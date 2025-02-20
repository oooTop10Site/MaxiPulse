package org.example.project.screens.tablet.sensor

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.mapper.toAiEvent
import org.example.project.data.mapper.toUI
import org.example.project.data.model.sensor.SensorResponse
import org.example.project.data.repository.AiAssistantManager
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.model.AiEvent
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.repository.AiAssistantRepository
import org.example.project.domain.repository.SensorRepository
import org.example.project.ext.toSensorPreviewUI
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.PlatformSocket
import org.example.project.platform.PlatformSocketListener
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.navigateEvent
import org.example.project.utils.Constants
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.getValue

internal class SensorViewModel : BaseScreenModel<SensorState, SensorEvent>(SensorState.InitState) {
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()
    val authManager: AuthManager by inject()
    val sensorRepository: SensorRepository by inject()
    val aiAssistant: AiAssistantRepository by inject()

    override fun onDispose() {
        super.onDispose()
        scanBluetoothSensorsManager.stopScan {  }
    }

    private val webSocket =
        PlatformSocket(Constants.BASE_SOCKET_URL, authManager.token.orEmpty())

    fun loadSensors() = intent {
        launchOperation(
            operation = {
                sensorRepository.getSensors()
            },
            success = {
                reduceLocal {
                    state.copy(
                        savedSensors = it
                    )
                }
            }
        )
    }

    fun addRemoteSensor(sensorUI: SensorUI) = intent {
        if (sensorUI.sensorId !in state.sensors.orEmpty().map { it.id }) {
            reduce {
                state.copy(
                    sensors = state.sensors.orEmpty() + sensorUI.toSensorPreviewUI(),
                    isLoading = false
                )
            }
        }
    }


    fun addSensor(sensorPreviewUI: SensorPreviewUI) = intent {
        launchOperation(
            operation = {
                sensorRepository.addSensor(mac = sensorPreviewUI.mac, name = sensorPreviewUI.name)
            },
            success = {
                reduceLocal {
                    state.copy(
                        savedSensors = state.savedSensors + sensorPreviewUI
                    )
                }
            }
        )
    }

}