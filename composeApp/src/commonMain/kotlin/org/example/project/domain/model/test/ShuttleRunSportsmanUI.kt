package org.example.project.domain.model.test

import androidx.compose.ui.graphics.Color
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chill
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.test_end
import org.jetbrains.compose.resources.StringResource

data class ShuttleRunSportsmanUI(
    val firstname: String,
    val lastname: String,
    val chss: Int,
    val status: ShuttleRunStatus
)

sealed class ShuttleRunStatus(val action: StringResource, val color: Color) {
    object Chill : ShuttleRunStatus(action = Res.string.chill, color = Color(0xFF8BADD1))
    object TestEnd : ShuttleRunStatus(action = Res.string.test_end, color = Color(0xFF808080))
    object Running :
        ShuttleRunStatus(action = Res.string.end, color = Color(0xFF83AF4D).copy(alpha = 0.8f))

}