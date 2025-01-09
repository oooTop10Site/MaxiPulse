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
import org.example.project.di.KoinInjector
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.component.KoinComponent
import kotlin.getValue

@SuppressLint("MissingPermission")
actual fun KoinComponent.scanBluetoothSensors(onDeviceFound: (SensorUI) -> Unit) {
    val context by KoinInjector.koin.inject<Context>()
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    // Проверка, поддерживает ли устройство Bluetooth
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
        return
    }

    val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.let {
                // Получаем имя устройства и его идентификатор
                val deviceName = it.device.name ?: "Unknown Device"
                val sensorId = it.device.address // Обычно используется MAC-адрес устройства
                // Создаем объект SensorUI и передаем в callback
                val sensor = SensorUI(sensorId, deviceName, SensorStatus.Active)
                onDeviceFound(sensor)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            // Обработка ошибок сканирования (если нужно)
        }
    }
    val scanFilters = listOf<ScanFilter>()
    // Стартуем сканирование BLE устройств
    val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()
    bluetoothLeScanner.startScan(scanFilters, scanSettings, scanCallback)

    // Рекомендуется остановить сканирование после определенного времени
    Handler(Looper.getMainLooper()).postDelayed({
        bluetoothLeScanner.stopScan(scanCallback)
    }, 15000) // Останавливаем сканирование через 10 секунд
}