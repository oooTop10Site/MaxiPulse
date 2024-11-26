package org.example.project.ext

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.friday
import maxipuls.composeapp.generated.resources.friday_sh
import maxipuls.composeapp.generated.resources.monday
import maxipuls.composeapp.generated.resources.monday_sh
import maxipuls.composeapp.generated.resources.saturday
import maxipuls.composeapp.generated.resources.saturday_sh
import maxipuls.composeapp.generated.resources.sunday
import maxipuls.composeapp.generated.resources.sunday_sh
import maxipuls.composeapp.generated.resources.thursday
import maxipuls.composeapp.generated.resources.thursday_sh
import maxipuls.composeapp.generated.resources.tuesday
import maxipuls.composeapp.generated.resources.tuesday_sh
import maxipuls.composeapp.generated.resources.wednesday
import maxipuls.composeapp.generated.resources.wednesday_sh
import org.jetbrains.compose.resources.StringResource

fun LocalDate.toUI(): String {
    return "${this.dayOfMonth.addZero()}.${this.monthNumber.addZero()}.${this.year}"
}

fun LocalDate.toUIDayOfMonth(): String {
    return "${this.dayOfMonth.addZero()}.${this.monthNumber.addZero()}"
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

fun DayOfWeek.toTextShort(): StringResource {
    return when (this) {
        DayOfWeek.MONDAY -> Res.string.monday_sh
        DayOfWeek.TUESDAY -> Res.string.tuesday_sh
        DayOfWeek.WEDNESDAY -> Res.string.wednesday_sh
        DayOfWeek.THURSDAY -> Res.string.thursday_sh
        DayOfWeek.FRIDAY -> Res.string.friday_sh
        DayOfWeek.SATURDAY -> Res.string.saturday_sh
        DayOfWeek.SUNDAY -> Res.string.sunday_sh
        else -> Res.string.monday_sh
    }
}

fun getCurrentWeekDates(): List<LocalDate> {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val currentDayOfWeek = currentDate.dayOfWeek
    val daysFromMonday = currentDayOfWeek.ordinal // Сколько дней прошло с понедельника
    val startOfWeek =
        currentDate.minus(DatePeriod(days = daysFromMonday)) // Начало недели (понедельник)
    return List(7) { startOfWeek.plus(DatePeriod(days = it)) }
}


