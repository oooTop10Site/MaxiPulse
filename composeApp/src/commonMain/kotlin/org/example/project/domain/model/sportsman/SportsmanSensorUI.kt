package org.example.project.domain.model.sportsman

data class SportsmanSensorUI(
    val id: String,
    val number: String,
    val name: String,
    val lastname: String,
    val middleName: String,
    val compositionNumber: Int,
    val sensor: SensorUI?,
)