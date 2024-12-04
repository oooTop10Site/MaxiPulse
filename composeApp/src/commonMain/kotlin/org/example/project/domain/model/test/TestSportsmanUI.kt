package org.example.project.domain.model.test

import androidx.compose.ui.graphics.Color
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chill
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.lying_position
import maxipuls.composeapp.generated.resources.standing_position
import maxipuls.composeapp.generated.resources.test_end
import org.jetbrains.compose.resources.StringResource

data class TestSportsmanUI(
    val firstname: String,
    val lastname: String,
    val chss: Int,
    val status: TestStatus
)

sealed class TestStatus(val action: StringResource, val color: Color) {
    class Chill(val timer: Long = 120) : TestStatus(action = Res.string.chill, color = Color(0xFF8BADD1))
    object TestEnd : TestStatus(action = Res.string.test_end, color = Color(0xFF808080))
    object Running :
        TestStatus(action = Res.string.end, color = Color(0xFF83AF4D).copy(alpha = 0.8f))

    object LyingPosition :    TestStatus(action = Res.string.lying_position, color = Color(0xFFD45470))
    object StandingPosition :    TestStatus(action = Res.string.standing_position, color = Color(0xFF669FDA))


}