package org.example.project.ext

import androidx.compose.ui.graphics.Color

fun Int.toAnalizeColor(): Color {
    return when (this) {
        in 0..140 -> Color(0xFF7ECA22)
        in 140..360 -> Color(0xFF3093F9)
        in 360..540 -> Color(0xFFDF0B40)
        in 540..800 -> Color(0xFF840928)
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

//fun Int.toRightMark(): Double {
//    var result = this
//    if (result > 140) {
//        result += result +
//    }
//}