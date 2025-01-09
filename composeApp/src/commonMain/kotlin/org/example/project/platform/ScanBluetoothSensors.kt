package org.example.project.platform

import androidx.compose.runtime.Composable
import org.example.project.domain.model.sportsman.SensorUI
import org.koin.core.component.KoinComponent

expect fun KoinComponent.scanBluetoothSensors(onDeviceFound: (SensorUI) -> Unit)