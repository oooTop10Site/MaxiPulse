package org.example.project.utils

fun Int?.orEmpty(default: Int = 0): Int {
    return this?: default
}

fun Float?.orEmpty(default: Float = 0f): Float {
    return this?: default
}

fun Long?.orEmpty(default: Long = 0): Long {
    return this?: default
}

fun Double?.orEmpty(default: Double = 0.0): Double {
    return this?: default
}