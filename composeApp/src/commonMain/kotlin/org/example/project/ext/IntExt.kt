package org.example.project.ext

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun Int.toAnalizeColor(): Color {
    return when (this) {
        in 0..140 -> Color(0xFF7ECA22)
        in 140..360 -> Color(0xFF3093F9)
        in 360..540 -> Color(0xFFDF0B40)
        in 540..800 -> Color(0xFF840928)
        else -> Color.Unspecified
    }
}

fun Float.toAnalizeLoadBackgroundColor(): Color {
    return when (this) {
        1400f -> Color(0xFFB9556F)
        1100f -> Color(0xFFE9557A)
        1050f -> Color(0xFFB6E181)
        700f -> Color(0xFFC7D8F7)
        else -> Color.Unspecified
    }
}


fun Double.toAnalizeTensionBackgroundColor(): Color {
    return when (this) {
        in 0.0..1.7 -> Color(0xFFB6E181)
        in 1.7..2.0 -> Color(0xFFFFBF6B)
        in 2.0..2.5 -> Color(0xFFE9557A)
        else -> Color.Unspecified
    }
}

fun Int.toAnalizeMorningRecoveryBackgroundColor(first: Int, second: Int, max: Int): Color {
    return when (this) {
        in 0..first -> Color(0xFFDF0B40)
        in first..second -> Color(0xFFFFA93A)
        in second..max -> Color(0xFF95D14B)
        else -> Color.Unspecified
    }
}


fun Double.toAnalizeTensionValueColor(): Color {
    return when (this) {
        in 0.0..1.7 -> Color(0xFFFF7E9F)
        in 1.7..1.85 -> Color(0xFFFF4170)
        in 1.85..1.95 -> Color(0xFFF1124A)
        in 2.0..2.5 -> Color(0xFF950025)
        else -> Color.Unspecified
    }
}


fun List<Int>.max(default: Int): Int {
    return try {
        this.max()
    } catch (e: Exception) {
        default
    }
}

fun <T, R : Comparable<R>> Iterable<T>.maxOf(default: R, selector: (T) -> R): R {
    return try {
        this.maxOf(selector = selector)
    } catch (e: Exception) {
        default
    }
}

fun <T, R : Comparable<R>> Iterable<T>.minOf(default: R, selector: (T) -> R): R {
    return try {
        this.minOf(selector = selector)
    } catch (e: Exception) {
        default
    }
}

fun Double.roundToIntOrNull(default: Int): Int {
    return try {
        this.roundToInt()
    } catch (e: Exception) {
        default
    }
}

fun Float.roundToIntOrNull(): Int? {
    return try {
        this.roundToInt()
    } catch (e: Exception) {
        null
    }
}

fun Double.rpeToColor(): Color {
    return when (this) {
        in 1.0..4.9 -> Color(0xFF95D14B)
        in 5.0..7.9 -> Color(0xFFFFA93A)
        in 8.0..10.0 -> Color(0xFFDF0B40)
        else -> {
            Color.Unspecified
        }
    }
}

fun Int.rpeToColor(): Color {
    return when (this) {
        in 1..4 -> Color(0xFF95D14B)
        in 5..7 -> Color(0xFFFFA93A)
        in 8..10 -> Color(0xFFDF0B40)
        else -> {
            Color.Unspecified
        }
    }
}
//fun Int.toRightMark(): Double {
//    var result = this
//    if (result > 140) {
//        result += result +
//    }
//}