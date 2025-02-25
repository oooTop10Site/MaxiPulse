package org.example.project.ext

import org.example.project.domain.model.sportsman.HeartBit

fun List<HeartBit>.getEvenlyDistributedMills(pointsCount: Int): List<Long> {
    if (this.isEmpty() || pointsCount <= 0) return emptyList()

    // Получаем минимальное и максимальное значение `mills`
    val minMills = this.minOf { it.mills }
    val maxMills = this.maxOf { it.mills }

    // Вычисляем шаг между точками
    val step = (maxMills - minMills) / (pointsCount - 1)

    // Генерируем равномерно распределенные значения `mills`
    return List(pointsCount) { index -> minMills + step * index }
}
