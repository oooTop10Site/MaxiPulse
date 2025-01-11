package org.example.project.domain.model.sportsman

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class SensorUI(
    val companyId: Int = 0,
    val runningCounter: Int = 0,
    val battery: Int = 0,
    val acc: Float = 0f,
    val heartRate: List<Int> = emptyList(),
    val rr: Int = 0,
    val sensorId: String,
    val deviceName: String,
    val status: SensorStatus,
): JavaSerializable {
    companion object {
        val Empty = SensorUI(
            0,
            0,
            0,
            0f,
            emptyList(),
            0,
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