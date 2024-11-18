package org.example.project.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun LocalDate.toUI(): String {
    return "${this.dayOfMonth.addZero()}.${this.monthNumber.addZero()}.${this.year}"
}

fun LocalTime.toUI(): String {
    return "${this.hour.addZero()}:${this.minute.addZero()}"
}

fun Long.toTimeUI(): String {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this / (1000 * 60)) % 60
    val seconds = (this / 1000) % 60

    return "${hours.addZero()}:${minutes.addZero()}:${seconds.addZero()}"
}


fun Int.addZero(): String {
    if (this < 10) {
        return "0$this"
    }
    return this.toString()
}

fun Long.addZero(): String {
    if (this < 10) {
        return "0$this"
    }
    return this.toString()
}


