package org.example.project.domain.model

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI

sealed class MainAlertDialog {
   object Question: MainAlertDialog()
   object PlayerWithNoActiveSensor: MainAlertDialog()
   data class SelectSensor(val sportsman: SportsmanSensorUI): MainAlertDialog()
   data class SensorAlreadyAssigned(val sensorUI: SensorUI, val sportsmanId: String): MainAlertDialog()
}