package org.example.project.platform

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.example.project.di.KoinInjector
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.component.KoinComponent
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.getValue
import kotlin.math.abs

internal actual class ScanBluetoothSensorsManager :
    KoinComponent {
    val context by KoinInjector.koin.inject<Context>()
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    var scanCallback: ScanCallback? = null

    @SuppressLint("MissingPermission")
    actual fun scanBluetoothSensors(
        onDeviceFound: (SensorUI) -> Unit,
    ) {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            stopScan {  }
            return
        }
//        stopScan() {
            scanCallback = object : ScanCallback() {
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
            bluetoothLeScanner.startScan(scanCallback)
//        }
    }

    @SuppressLint("MissingPermission")
    actual fun stopScan(doAfter: () -> Unit) {
        scanCallback?.let {
            println("STOP SCAN")
            bluetoothLeScanner.stopScan(it)
            doAfter()
        } ?: doAfter()
    }
}

fun decodeSensorData(dataList: List<Int>, sensorUI: SensorUI): SensorUI {
    val byteArray = dataList.map { it.toByte() }.toByteArray()
    val buffer = ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN)

    val companyId = buffer.getShort(0).toUShort()
    val battery = buffer.get(byteArray.size - 14).toInt()
    val runningCounter = buffer.getInt(byteArray.size - 12).toUInt()
    val value1Acc = buffer.getFloat(byteArray.size - 8)
    val heartRate = buffer.get(byteArray.size - 4).toInt()
    val rr = buffer.getShort(byteArray.size - 2).toInt()

    return sensorUI.copy(
        companyId = companyId.toInt(),
        battery = battery,
        runningCounter = runningCounter.toInt(),
        acc = value1Acc,
        heartRate = sensorUI.heartRate + abs(heartRate),
        rr = rr
    )
}

