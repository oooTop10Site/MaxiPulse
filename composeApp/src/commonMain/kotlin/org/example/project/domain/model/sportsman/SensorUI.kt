package org.example.project.domain.model.sportsman

data class SensorUI(
    val sensorId: String,
) {
    companion object {
        val Empty = SensorUI("")
    }
}