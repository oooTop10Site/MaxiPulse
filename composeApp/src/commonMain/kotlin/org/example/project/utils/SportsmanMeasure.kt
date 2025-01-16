package org.example.project.utils

import org.example.project.domain.model.sportsman.HeartBit
import kotlin.math.pow
import kotlin.math.roundToInt

object SportsmanMeasure {
    // Пример функции для расчета МПК
    fun calculateMPK(maxHeartRate: Int, age: Int): Int {
        val maxHR = maxHeartRate
        return ((15.3 * maxHR) / (220 - age)).toInt() // Пример формулы МПК
    }


    // ЧСС ПАО (аэробный порог): Среднее значение пульса в 50–70% от ЧСС максимального
    fun calculateChssPao(heartRates: List<HeartBit>, heartRateMax: Int): Int {
        if (heartRates.isEmpty() || heartRateMax == 0) return 0

        val aerobicRange = IntRange(
            (heartRateMax * 0.5).toInt(),
            (heartRateMax * 0.7).toInt()
        )

        val aerobicHeartRates = heartRates.filter { it.value in aerobicRange }
        return aerobicHeartRates.map { it.value }.average().toInt()
    }

    // ЧСС ПАНО (анаэробный порог): Среднее значение пульса в 80–90% от ЧСС максимального
    fun calculateChssPano(heartRates: List<HeartBit>, heartRateMax: Int): Int {
        if (heartRates.isEmpty() || heartRateMax == 0) return 0

        val anaerobicRange = IntRange(
            (heartRateMax * 0.8).toInt(),
            (heartRateMax * 0.9).toInt()
        )

        val anaerobicHeartRates = heartRates.filter { it.value in anaerobicRange }
        return anaerobicHeartRates.map { it.value }.average().toInt()
    }

    fun trimp(
        isMale: Boolean,
        trainingTimeSeconds: Long,
        chssReasting: Int,
        chssMaxOnTraining: Int,
        chssMax: Int
    ): Double {

        println("СЧИТАЕМ ТРИМП")
        println("chssMaxOnTraining - $chssMaxOnTraining")
        val reserveChss = (chssMaxOnTraining - chssReasting).toDouble() / (chssMax - chssReasting).toDouble()
        val t = trainingTimeSeconds.toDouble() / 60.0
        val a = if (isMale) 0.64 else 0.86
        val b = if (isMale) 1.92 else 1.67
        val e = 2.718
        println("reserveChss - $reserveChss")
        println("timeMin - $t")
        return (t * reserveChss * a * e.pow(reserveChss * b)*100).roundToInt() / 100.0
    }

}