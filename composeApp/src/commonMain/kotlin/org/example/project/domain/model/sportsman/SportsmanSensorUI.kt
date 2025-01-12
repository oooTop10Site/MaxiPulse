package org.example.project.domain.model.sportsman

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import org.example.project.utils.orEmpty
import kotlin.math.roundToInt

data class SportsmanSensorUI(
    val id: String,
    val number: String,
    val name: String,
    val lastname: String,
    val middleName: String,
    val compositionNumber: String,
    val sensor: SensorUI?,
    val avatar: String,
    val age: Int,
    val isMale: Boolean,
    val heartRateMax: Int = 230,
    val heartRateMin: Int = 0,
    val isTraining: Boolean = false,
) : JavaSerializable {

    val zone1Range: List<Int> = IntRange(0, 70).toList()
    val zone2Range: List<Int> = IntRange(70, 90).toList()
    val zone3Range: List<Int> = IntRange(90, 110).toList()
    val zone4Range: List<Int> = IntRange(110, 130).toList()
    val zone5Range: List<Int> = IntRange(130, 230).toList()

    private fun calculateTimeInZone(range: List<Int>): Long {
        val heartRates = sensor?.heartRate.orEmpty()
        if (heartRates.size < 2) return 0L

        var totalTime = 0L

        for (i in 0 until heartRates.size - 1) {
            val current = heartRates[i]
            val next = heartRates[i + 1]

            if (current.value in range) {
                totalTime += (next.mills - current.mills)
            }
        }

        return totalTime / 1000 // Конвертируем миллисекунды в секунды
    }

    val zone1: Long
        get() = calculateTimeInZone(zone1Range)

    val zone2: Long
        get() = calculateTimeInZone(zone2Range)

    val zone3: Long
        get() = calculateTimeInZone(zone3Range)

    val zone4: Long
        get() = calculateTimeInZone(zone4Range)

    val zone5: Long
        get() = calculateTimeInZone(zone5Range)

    val timeTrainingSeconds: Long
        get() = (if (sensor?.heartRate?.size.orEmpty() >= 2) {
            sensor?.heartRate?.lastOrNull()?.mills.orEmpty() - sensor?.heartRate?.firstOrNull()?.mills.orEmpty()
        } else 0) / 1000

    val maxHeartRate: Int
        get() = sensor?.heartRate.orEmpty().maxOf { it.value }

    val minHeartRate: Int
        get() = sensor?.heartRate.orEmpty().minOf { it.value }

    val avgHeartRate: Int
        get() = sensor?.heartRate.orEmpty().map { it.value }.average().roundToInt()

}