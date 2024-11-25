package org.example.project.domain.model.utp

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.DayOfWeek
import org.example.project.utils.Constants

data class DayUtpUI(
    val day: DayOfWeek,
    @IntRange(from = 0) val progressTraining: Int,
) {
    companion object {
        val Default = DayUtpUI(
            day = DayOfWeek.MONDAY,
            progressTraining = 0,
        )
    }

    val color: Color
        get() = when (progressTraining) {
            in 0..99 -> Color(0xFFBFE591)
            in 100..150 -> Color(0xFF98C9FC)
            in 151..199 -> Color(0xFFEF85A0)
            Constants.MAX_TRAINING_INTENSITY -> Color(0xFFC28594)
            else -> {
                Color(0xFFBFE591)
            }
        }
}