package org.example.project.ext

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.friday
import maxipuls.composeapp.generated.resources.monday
import maxipuls.composeapp.generated.resources.saturday
import maxipuls.composeapp.generated.resources.sunday
import maxipuls.composeapp.generated.resources.thursday
import maxipuls.composeapp.generated.resources.tuesday
import maxipuls.composeapp.generated.resources.wednesday
import org.jetbrains.compose.resources.StringResource

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

fun LocalDate.toServer(): String {
    return "${this.year}-${this.monthNumber.addZero()}-${this.dayOfMonth.addZero()}"
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

fun DayOfWeek.toText(): StringResource {
    return when (this) {
        DayOfWeek.MONDAY -> Res.string.monday
        DayOfWeek.TUESDAY -> Res.string.tuesday
        DayOfWeek.WEDNESDAY -> Res.string.wednesday
        DayOfWeek.THURSDAY -> Res.string.thursday
        DayOfWeek.FRIDAY -> Res.string.friday
        DayOfWeek.SATURDAY -> Res.string.saturday
        DayOfWeek.SUNDAY -> Res.string.sunday
        else -> Res.string.monday
    }
}



