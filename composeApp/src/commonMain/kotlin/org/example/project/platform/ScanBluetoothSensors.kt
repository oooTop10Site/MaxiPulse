package org.example.project.platform

import androidx.compose.runtime.Composable
import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal expect class ScanBluetoothSensorsManager() {

    @Composable
    fun scanSensors(
        onCatch: (Throwable) -> Unit = {},
        onDeviceFound: (SensorUI) -> Unit,
    )

    @Composable
    fun scanBluetoothSensors(
        onCatch: (Throwable) -> Unit = {},
        onDeviceFound: (SensorUI) -> Unit,
    )

    fun scanSocketSensors(
        onCatch: (Throwable) -> Unit = {},
        onDeviceFound: (SensorUI) -> Unit,
    )

    fun stopScan(doAfter: () -> Unit)
}
