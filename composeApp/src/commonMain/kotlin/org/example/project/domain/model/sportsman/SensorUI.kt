package org.example.project.domain.model.sportsman

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.utils.orEmpty

data class SensorUI(
    val companyId: Int = 0,
    val runningCounter: Int = 0,
    val battery: Int = 0,
    val acc: Float = 0f,
    val heartRate: List<HeartBit> = emptyList(),
    val rr: Int = 0,
    val sensorId: String,
    val deviceName: String,
    val status: SensorStatus,
) : JavaSerializable {
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
            SensorStatus.Disable
        )
    }


}

enum class SensorStatus : JavaSerializable {
    Disable,
    Active,
    Unknown
}