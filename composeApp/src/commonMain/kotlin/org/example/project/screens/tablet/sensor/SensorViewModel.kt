package org.example.project.screens.tablet.sensor

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.mapper.toUI
import org.example.project.data.model.sensor.SensorResponse
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.repository.SensorRepository
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.PlatformSocket
import org.example.project.platform.PlatformSocketListener
import org.example.project.utils.Constants
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.getValue

internal class SensorViewModel : BaseScreenModel<SensorState, SensorEvent>(SensorState.InitState) {
    val authManager: AuthManager by inject()
    val sensorRepository: SensorRepository by inject()
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

    fun connectSocket() {
        println("алоха я тут")
        webSocket.openSocket(object : PlatformSocketListener {
            override fun onFailure(t: Throwable) {
                println("socket - ${t.message}")
                println("socket - ${t.cause}")
                webSocket.closeSocket(1000, "")
//                connectionAborted = true
                screenModelScope.launch {
                    delay(3000L)
                    connectSocket()
                }
            }

            override fun onOpen() {
                println("socket - connect")
            }

            override fun onClosing(code: Int, reason: String) {
                println("socket - onClosing")
            }

            override fun onMessage(msg: String) {
                println("socket - onMessage")
                handleMessage(msg)
            }

            override fun onClosed(code: Int, reason: String) {
                println("socket - onClosed")
            }

        })
    }

    fun handleMessage(msg: String) = intent() {
        /* Реализовать получение типа */
        /**
         * { "type": "message", "data": {"_response":{"message":"Сообщение успешно найдено","data":{"message":"тиииии","role":"role:user","created_at":"2024-08-21T14:20:26.000000Z"}}} }*/
        val response = Json { ignoreUnknownKeys = true }.decodeFromString<List<SensorResponse>>(msg)
        reduce {
            state.copy(
                sensors = response.map { it.toUI() },
                isLoading = false
            )
        }
    }

}