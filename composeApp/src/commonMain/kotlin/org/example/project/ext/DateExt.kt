package org.example.project.ext

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlinx.serialization.descriptors.PrimitiveKind
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.april
import maxipuls.composeapp.generated.resources.august
import maxipuls.composeapp.generated.resources.by_april
import maxipuls.composeapp.generated.resources.by_august
import maxipuls.composeapp.generated.resources.by_december
import maxipuls.composeapp.generated.resources.by_february
import maxipuls.composeapp.generated.resources.by_january
import maxipuls.composeapp.generated.resources.by_july
import maxipuls.composeapp.generated.resources.by_june
import maxipuls.composeapp.generated.resources.by_march
import maxipuls.composeapp.generated.resources.by_may
import maxipuls.composeapp.generated.resources.by_november
import maxipuls.composeapp.generated.resources.by_october
import maxipuls.composeapp.generated.resources.by_september
import maxipuls.composeapp.generated.resources.december
import maxipuls.composeapp.generated.resources.february
import maxipuls.composeapp.generated.resources.friday
import maxipuls.composeapp.generated.resources.friday_sh
import maxipuls.composeapp.generated.resources.january
import maxipuls.composeapp.generated.resources.july
import maxipuls.composeapp.generated.resources.june
import maxipuls.composeapp.generated.resources.march
import maxipuls.composeapp.generated.resources.may
import maxipuls.composeapp.generated.resources.monday
import maxipuls.composeapp.generated.resources.monday_sh
import maxipuls.composeapp.generated.resources.november
import maxipuls.composeapp.generated.resources.october
import maxipuls.composeapp.generated.resources.saturday
import maxipuls.composeapp.generated.resources.saturday_sh
import maxipuls.composeapp.generated.resources.september
import maxipuls.composeapp.generated.resources.sunday
import maxipuls.composeapp.generated.resources.sunday_sh
import maxipuls.composeapp.generated.resources.thursday
import maxipuls.composeapp.generated.resources.thursday_sh
import maxipuls.composeapp.generated.resources.tuesday
import maxipuls.composeapp.generated.resources.tuesday_sh
import maxipuls.composeapp.generated.resources.wednesday
import maxipuls.composeapp.generated.resources.wednesday_sh
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

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

fun Long.secondsToUI(): String {
    val hours = this / (1 * 60 * 60)
    val minutes = (this / (1 * 60)) % 60
    val seconds = (this / 1) % 60

    return "${hours.addZero()}:${minutes.addZero()}:${seconds.addZero()}"
}

fun Int.secondsToUI(): String {
    val hours = this / (1 * 60 * 60)
    val minutes = (this / (1 * 60)) % 60
    val seconds = (this / 1) % 60

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

fun LocalDate.generateCalendarGrid(): List<LocalDate> {
    val firstDayOfMonth = LocalDate(year, month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek

    // Определяем дату начала массива (понедельник перед началом месяца)
    val startOffset = firstDayOfWeek.ordinal // ordinal: Monday=0, Sunday=6
    val startDate = firstDayOfMonth.minus(startOffset.toLong(), DateTimeUnit.DAY)

    // Генерируем 35 дней подряд (5 недель)
    return List(35) { startDate.plus(it.toLong(), DateTimeUnit.DAY) }
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

fun Month?.toByText(): StringResource {
    return when (this) {
        Month.JANUARY -> Res.string.by_january
        Month.FEBRUARY -> Res.string.by_february
        Month.MARCH -> Res.string.by_march
        Month.APRIL -> Res.string.by_april
        Month.MAY -> Res.string.by_may
        Month.JUNE -> Res.string.by_june
        Month.JULY -> Res.string.by_july
        Month.AUGUST -> Res.string.by_august
        Month.SEPTEMBER -> Res.string.by_september
        Month.OCTOBER -> Res.string.by_october
        Month.NOVEMBER -> Res.string.by_november
        Month.DECEMBER -> Res.string.by_december
        else -> {
            Res.string.by_january
        }
    }
}

fun Month?.toText(): StringResource {
    return when (this) {
        Month.JANUARY -> Res.string.january
        Month.FEBRUARY -> Res.string.february
        Month.MARCH -> Res.string.march
        Month.APRIL -> Res.string.april
        Month.MAY -> Res.string.may
        Month.JUNE -> Res.string.june
        Month.JULY -> Res.string.july
        Month.AUGUST -> Res.string.august
        Month.SEPTEMBER -> Res.string.september
        Month.OCTOBER -> Res.string.october
        Month.NOVEMBER -> Res.string.november
        Month.DECEMBER -> Res.string.december
        else -> {
            Res.string.january // По умолчанию Январь
        }
    }
}


fun LocalDate.calculateAge(): Int {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return this.until(today, DateTimeUnit.YEAR)
}

fun getCurrentWeekDates(): List<LocalDate> {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val currentDayOfWeek = currentDate.dayOfWeek
    val daysFromMonday = currentDayOfWeek.ordinal // Сколько дней прошло с понедельника
    val startOfWeek =
        currentDate.minus(DatePeriod(days = daysFromMonday)) // Начало недели (понедельник)
    return List(7) { startOfWeek.plus(DatePeriod(days = it)) }
}

fun LocalDate.weekDates(): List<LocalDate> {
    val currentDate = this
    val currentDayOfWeek = currentDate.dayOfWeek
    val daysFromMonday = currentDayOfWeek.ordinal // Сколько дней прошло с понедельника
    val startOfWeek =
        currentDate.minus(DatePeriod(days = daysFromMonday)) // Начало недели (понедельник)
    return List(7) { startOfWeek.plus(DatePeriod(days = it)) }
}


@Composable
fun LocalDate.toDayOfWeekFullDate(): String {
    return "${stringResource(this.dayOfWeek.toText())}, ${this.toUI()}"
}

fun Long.formatSeconds(returnEmptyIfZero: Boolean = false): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60

    val result = buildString {
        if (hours != 0L) {
            append(if (hours < 10) "0$hours" else "$hours")
            append(":")
        }
        append(if (minutes < 10) "0$minutes" else "$minutes")
        append(":")
        append(if (remainingSeconds < 10) "0$remainingSeconds" else "$remainingSeconds")
    }

    return if (returnEmptyIfZero && this == 0L) {
        ""
    } else result
}

