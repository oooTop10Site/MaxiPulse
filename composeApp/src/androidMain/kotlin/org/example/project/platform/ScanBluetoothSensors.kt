package org.example.project.platform

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.turn_on_bluetooth
import maxipuls.composeapp.generated.resources.turn_on_gps
import org.example.project.data.mapper.toSensorUI
import org.example.project.data.model.sensor.SensorResponse
import org.example.project.di.KoinInjector
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.ext.granted
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.getValue
import kotlin.math.abs

internal actual class ScanBluetoothSensorsManager :
    KoinComponent {
    val context by KoinInjector.koin.inject<Context>()
    val bluetoothAdapter =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    var scanCallback: ScanCallback? = null
    val permissionService: PermissionsService by inject()
    val authManager: AuthManager by inject()
    val observerManager: MessageObserverManager by inject()
    private val webSocket = PlatformSocket(Constants.BASE_SOCKET_URL, authManager.token.orEmpty())

    @Composable
    actual fun scanSensors(
        onCatch: (Throwable) -> Unit,
        onDeviceFound: (SensorUI) -> Unit,
    ) {

        if (Constants.IsDataSocketFromSensor) {
            scanSocketSensors(onCatch, onDeviceFound)
        } else {
            scanBluetoothSensors(onCatch = onCatch, onDeviceFound = onDeviceFound)
        }

    }

    @SuppressLint("MissingPermission")
    @Composable
    actual fun scanBluetoothSensors(
        onCatch: (Throwable) -> Unit,
        onDeviceFound: (SensorUI) -> Unit,
    ) {
        println("ЗАШЛИ В САМУ ФУНКЦИЮ")
        var sensorShow by remember { mutableStateOf(false) }
        var sensorPermission by remember { mutableStateOf(false) }
        val turnOnGps = stringResource(Res.string.turn_on_gps)
        val turnOnBluetooth = stringResource(Res.string.turn_on_bluetooth)
        permissionService.checkPermissionFlow(Permission.BLUETOOTH_CONNECT)
            .collectAsState(permissionService.checkPermission(Permission.BLUETOOTH_CONNECT))
            .granted {
                if (sensorPermission) {
                    sensorShow = true
                }
            }
        LaunchedEffect(Unit) {
            if (permissionService.checkPermission(Permission.BLUETOOTH_CONNECT)
                    .granted()
            ) {
                sensorShow = true
            } else {
                sensorPermission = true
                permissionService.providePermission(Permission.BLUETOOTH_CONNECT)
            }
            delay(1500L)
            launch {
                locationStatusFlow().collect {
                    if (!it) {
                        observerManager.putMessage(turnOnGps)
                        onCatch(Throwable())
                        return@collect
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            var tempBluetoothAdapter = bluetoothAdapter
            var tempBluetoothAdapterEnabled = bluetoothAdapter.isEnabled
            while (true) {
                delay(1000L)
                println()
                if (!(bluetoothAdapter == null || !bluetoothAdapter.isEnabled) &&
                    locationStatusFlow().firstOrNull() == true &&
                    (tempBluetoothAdapter != bluetoothAdapter || tempBluetoothAdapterEnabled != bluetoothAdapter.isEnabled)) {
                    println("Я БЛЯ ТУТ НАХ ЛОВИ")
                    createCallBack(onDeviceFound, onCatch, turnOnBluetooth, turnOnGps) {
                        sensorShow = it
                    }
                }
                if(tempBluetoothAdapterEnabled && !bluetoothAdapter.isEnabled) {
                    observerManager.putMessage(turnOnBluetooth)
                }
                tempBluetoothAdapter = bluetoothAdapter
                tempBluetoothAdapterEnabled = bluetoothAdapter.isEnabled
            }
        }

        println("SensorShowVAR - $sensorShow")
        LaunchedEffect(sensorShow) {
            if (sensorShow) {

//        stopScan() {
                createCallBack(onDeviceFound, onCatch, turnOnBluetooth, turnOnGps) {
                    sensorShow = it
                }
//        }
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun createCallBack(
        onDeviceFound: (SensorUI) -> Unit,
        onCatch: (Throwable) -> Unit,
        turnOnBluetooth: String,
        turnOnGps: String,
        changeSensorShow: (Boolean) -> Unit
    ) {
        if (locationStatusFlow().firstOrNull() != true) {
            stopScan { }
            observerManager.putMessage(turnOnGps)
            onCatch(Throwable())
            changeSensorShow(false)
            return
        }
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            stopScan { }
            observerManager.putMessage(turnOnBluetooth)
            onCatch(Throwable())
            changeSensorShow(false)
            return
        }
        stopScan { }
        delay(1500L)
        scanCallback = object : ScanCallback() {
            @SuppressLint("MissingPermission")
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                Log.d("SCANDEVICE", "-------------------")
                super.onScanResult(callbackType, result)
                val scanRecord = result.scanRecord
                val rssi = result.rssi

                val manufacturerSpecificData = scanRecord?.manufacturerSpecificData

                manufacturerSpecificData?.let { data ->
                    for (i in 0 until data.size()) {
                        val manufacturerId = data.keyAt(i)
                        val manufacturerBytes = data.valueAt(i)
                        if (manufacturerId == 159) {
                            // Получаем имя устройства и его идентификатор
                            val deviceName = result.device.name ?: "Unknown Device"
                            val sensorId =
                                result.device.address // Обычно используется MAC-адрес устройства
                            // Создаем объект SensorUI и передаем в callback
                            val sensor = SensorUI(
                                sensorId = sensorId,
                                deviceName = deviceName,
                                status = SensorStatus.Active
                            )
                            try {
                                val decodedData = decodeSensorData(
                                    manufacturerBytes.map { it.toInt() },
                                    sensorUI = sensor
                                )
                                onDeviceFound(decodedData)
                            } catch (e: Exception) {
                                Log.e(
                                    "SCANDEVICE",
                                    "Failed to decode sensor data: ${e.message}"
                                )
                            }
                        }
                    }
                } ?: Log.w("SCANDEVICE", "No manufacturer-specific data found")
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                // Обработка ошибок сканирования (если нужно)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bluetoothLeScanner.startScan(
                null, ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // Или другой режим
                    .setLegacy(false) // Использовать BLE 5.x
                    .setPhy(ScanSettings.PHY_LE_ALL_SUPPORTED) // Coded PHY для дальнего действия
                    .build(), scanCallback
            )
        }
    }

    actual fun scanSocketSensors(
        onCatch: (Throwable) -> Unit, onDeviceFound: (SensorUI) -> Unit,
    ) {
        webSocket.openSocket(object : PlatformSocketListener {
            override fun onFailure(t: Throwable) {
                webSocket.closeSocket(1000, "")
//                connectionAborted = true
                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000L)
                    scanSocketSensors(onCatch, onDeviceFound)
                }
            }

            override fun onOpen() {
            }

            override fun onClosing(code: Int, reason: String) {
            }

            override fun onMessage(msg: String) {
                handleMessage(msg, onDeviceFound = onDeviceFound)
            }

            override fun onClosed(code: Int, reason: String) {
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
                onDeviceFound(it.toSensorUI(Clock.System.now().toEpochMilliseconds()))
            }
        }
    }

    @SuppressLint("MissingPermission")
    actual fun stopScan(doAfter: () -> Unit) {
        println("СТОПИМ")
        webSocket.closeSocket(code = 1000, reason = "message")
        scanCallback?.let {
            println("STOP SCAN")
            try {
                bluetoothLeScanner.stopScan(it)
            } catch (e: Exception) {
            }
            doAfter()
        } ?: doAfter()
        scanCallback = null
    }
}

fun decodeSensorData(dataList: List<Int>, sensorUI: SensorUI): SensorUI {
    val byteArray = dataList.map { it.toByte() }.toByteArray()
    val buffer = ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN)

    val companyId = buffer.getShort(0).toUShort()
    val battery = buffer.get(byteArray.size - 14).toInt()
    val runningCounter = buffer.getInt(byteArray.size - 12).toUInt()
    val value1Acc = buffer.getFloat(byteArray.size - 8)
    val heartRate = buffer.get(byteArray.size - 4).toInt() and 0xFF
    val rr = buffer.getShort(byteArray.size - 2).toInt()

    return sensorUI.copy(
        companyId = companyId.toInt(),
        battery = battery,
        runningCounter = runningCounter.toInt(),
        acc = value1Acc,
        heartRate = sensorUI.heartRate + HeartBit(
            value = abs(heartRate),
            mills = Clock.System.now().toEpochMilliseconds()
        ),
        rr = rr
    )
}