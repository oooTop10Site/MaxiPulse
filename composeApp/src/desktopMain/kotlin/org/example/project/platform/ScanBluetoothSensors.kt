package org.example.project.platform

import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.component.KoinComponent

internal actual class ScanBluetoothSensorsManager :
    KoinComponent {

    actual fun scanBluetoothSensors(
        onDeviceFound: (SensorUI) -> Unit,
    ) {

    }

    actual fun stopScan() {

    }
}