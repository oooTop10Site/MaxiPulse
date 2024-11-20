package org.example.project.utils

fun Int?.orEmpty(default: Int = 0): Int {
    return this ?: default
}

fun Float?.orEmpty(default: Float = 0f): Float {
    return this ?: default
}

fun Long?.orEmpty(default: Long = 0): Long {
    return this ?: default
}

fun Double?.orEmpty(default: Double = 0.0): Double {
    return this ?: default
}

fun Int.toStringWithCondition(emptyStringCondition: List<Int> = listOf<Int>(0)): String {
    return this.takeIf { it !in emptyStringCondition }?.toString() ?: ""
}

fun Double.toStringWithCondition(emptyStringCondition: List<Double> = listOf<Double>(0.0)): String {
    return this.takeIf { it !in emptyStringCondition }?.toString() ?: ""
}

fun Double.nonZero(): Boolean {
    return this != 0.0
}

fun Int.nonZero(): Boolean {
    return this != 0
}

fun Long.nonZero(): Boolean {
    return this != 0L
}