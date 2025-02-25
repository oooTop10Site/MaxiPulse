package org.example.project.domain.model.training

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.utils.Constants
import org.example.project.utils.orEmpty
import kotlin.collections.orEmpty

data class TrainingUI(
    val sensorUI: SensorUI,
    val duration: Long,
): JavaSerializable {
    companion object {
        val Default = TrainingUI(
            sensorUI = SensorUI.Empty,
            duration = 0,
        )
    }

    val zone1Range: List<Int> = Constants.zone1Range
    val zone2Range: List<Int> = Constants.zone2Range
    val zone3Range: List<Int> = Constants.zone3Range
    val zone4Range: List<Int> = Constants.zone4Range
    val zone5Range: List<Int> = Constants.zone5Range

    private fun calculateTimeInZone(range: List<Int>): Long {
        val heartRates = sensorUI.heartRate.orEmpty()
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
        get() = (if (sensorUI?.heartRate?.size.orEmpty() >= 2) {
            sensorUI?.heartRate?.lastOrNull()?.mills.orEmpty() - sensorUI?.heartRate?.firstOrNull()?.mills.orEmpty()
        } else 0) / 1000
}