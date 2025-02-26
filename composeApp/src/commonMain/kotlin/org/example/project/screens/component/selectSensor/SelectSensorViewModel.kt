package org.example.project.screens.component.selectSensor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.platform.ScanBluetoothSensorsManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import kotlin.getValue

internal class SelectSensorViewModel : ViewModel(), KoinComponent {
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()

    private val _state = MutableStateFlow(SelectSensorState.InitState)

    val state
        get() = _state.asStateFlow()

    fun changeSelectSensor(sensorId: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(
                selectSensor = sensorId
            ))
        }
    }

    fun updateSensor(sensorUI: SensorUI) {
        println("state.devices - ${state.value.devices}")
        viewModelScope.launch {
            if (sensorUI.sensorId !in _state.value.devices.map { it.sensorId }) {
                _state.emit(
                    _state.value.copy(
                        devices = _state.value.devices + sensorUI
                    )
                )
            } else {
                _state.emit(
                    _state.value.copy(
                        devices = _state.value.devices.map {
                            if (it.sensorId == sensorUI.sensorId) {
                                it.copy(heartRate = it.heartRate + sensorUI.heartRate)
                            } else it
                        }
                    )
                )
            }
        }
    }

}