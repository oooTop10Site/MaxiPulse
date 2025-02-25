package org.example.project.domain.model.questionnaire

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.empty_level
import maxipuls.composeapp.generated.resources.high_level
import maxipuls.composeapp.generated.resources.no_high_level
import org.jetbrains.compose.resources.StringResource

data class SportsmanQuestionnaireUI(
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val fear: Int,
    val avoid: Int,
    val anxiety: Int,
    val date: LocalDate
) : JavaSerializable {

    val fio: String
        get() = "$lastname $firstname $middlename"

    val fearColor: Color
        get() = when (fear) {
            else -> Color(0xFFDF0B40).copy(alpha = 0.3f)
        }

    val avoidColor: Color
        get() = when (avoid) {
            else -> Color(0xFFDF0B40).copy(alpha = 0.3f)
        }

    val anxietyColor: Color
        get() = when (anxiety) {
            else -> Color(0xFFDF0B40).copy(alpha = 0.3f)
        }

    val fearText: StringResource
        get() = when (fear) {
            else -> Res.string.high_level
        }

    val avoidText: StringResource
        get() = when (avoid) {
            else -> Res.string.no_high_level
        }

    val anxietyText: StringResource
        get() = when (anxiety) {
            else -> Res.string.empty_level
        }
}