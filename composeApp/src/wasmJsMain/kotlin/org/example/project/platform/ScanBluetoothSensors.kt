package org.example.project.platform

import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.component.KoinComponent

actual fun KoinComponent.scanBluetoothSensors(onDeviceFound: (SensorUI) -> Unit, endScan: (() -> Unit)) {}