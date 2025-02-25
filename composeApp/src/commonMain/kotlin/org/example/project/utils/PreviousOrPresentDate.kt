package org.example.project.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
fun previousOrPresentSelectableDates(instant: Instant): SelectableDates {
    return object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            println("$utcTimeMillis >= ${instant.toEpochMilliseconds() - instant.toLocalDateTime(TimeZone.UTC).time.toMillisecondOfDay()}")
            return utcTimeMillis <= instant.toEpochMilliseconds() - instant.toLocalDateTime(TimeZone.UTC).time.toMillisecondOfDay()
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year <= instant.toLocalDateTime(TimeZone.UTC).year
        }
    }
}