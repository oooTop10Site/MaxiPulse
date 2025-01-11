package org.example.project.utils

import kotlin.math.pow

object SportsmanMeasure {

    fun trimp(
        isMale: Boolean,
        trainingTimeSeconds: Long,
        chssReasting: Int,
        chssMaxOnTraining: Int,
        chssMax: Int
    ): Double {
        val reserveChss = (chssMaxOnTraining - chssReasting) / (chssMax - chssReasting)
        val t = trainingTimeSeconds.toDouble() / 60.0
        val a = if (isMale) 0.64 else 0.86
        val b = if (isMale) 1.92 else 1.67
        val e = 2.718
        println(reserveChss)
        println("timeMin - t")
        return t * reserveChss * a * e.pow(reserveChss * b)
    }

}