package org.example.project.domain.model.sportsman

data class SensorUI(
    val sensorId: String,
    val deviceName: String,
    val status: SensorStatus
) {
    companion object {
        val Empty = SensorUI(
            "",
            "",
            SensorStatus.Disable)
    }
}

enum class SensorStatus {
    Disable,
    Active,
    Unknown
}