package org.example.project.platform

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import org.example.project.data.mapper.toSensorUI
import org.example.project.data.mapper.toUI
import org.example.project.data.model.sensor.SensorResponse
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.repository.SensorRepository
import org.example.project.utils.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.collections.map
import kotlin.getValue

internal actual class ScanBluetoothSensorsManager :
    KoinComponent {
    val authManager: AuthManager by inject()
    private val webSocket = PlatformSocket(Constants.BASE_SOCKET_URL, authManager.token.orEmpty())
    actual fun scanBluetoothSensors(
        onCatch: (Throwable) -> Unit,
        onDeviceFound: (SensorUI) -> Unit,
    ) {
        connectSocket(onCatch = onCatch, onDeviceFound = onDeviceFound)
    }

    actual fun stopScan(doAfter: () -> Unit) {
        webSocket.closeSocket(1000, "ЗАКРЫЛ РУЧКАМИ")
    }

    fun connectSocket(
        onDeviceFound: (SensorUI) -> Unit, onCatch: (Throwable) -> Unit
    ) {
        println("алоха я тут")
        webSocket.openSocket(object : PlatformSocketListener {
            override fun onFailure(t: Throwable) {
                println("socket - ${t.message}")
                println("socket - ${t.cause}")
                webSocket.closeSocket(1000, "")
//                connectionAborted = true
                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000L)
                    connectSocket(onDeviceFound, onCatch)
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
                handleMessage(msg, onDeviceFound = onDeviceFound)
            }

            override fun onClosed(code: Int, reason: String) {
                println("socket - onClosed")
            }

        })
    }

    fun handleMessage(msg: String, onDeviceFound: (SensorUI) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            /* Реализовать получение типа */
            /**
             * { "type": "message", "data": {"_response":{"message":"Сообщение успешно найдено","data":{"message":"тиииии","role":"role:user","created_at":"2024-08-21T14:20:26.000000Z"}}} }*/
            val response =
                Json { ignoreUnknownKeys = true }.decodeFromString<List<SensorResponse>>(msg)
            response.forEach {
                onDeviceFound(it.toSensorUI())
            }
        }
    }
}