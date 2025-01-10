package org.example.project.platform

import androidx.compose.runtime.Composable
import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal expect class ScanBluetoothSensorsManager() {
    fun scanBluetoothSensors(onDeviceFound: (SensorUI) -> Unit)
    fun stopScan()
}
