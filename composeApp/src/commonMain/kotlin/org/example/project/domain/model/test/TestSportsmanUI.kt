package org.example.project.domain.model.test

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chill
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.lying_position
import maxipuls.composeapp.generated.resources.standing_position
import maxipuls.composeapp.generated.resources.test_end
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestStatus.Running
import org.example.project.domain.model.test.TestStatus.TestEnd
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.StringResource

data class TestSportsmanUI(
    val sportsmanSensorUI: SportsmanSensorUI,
) : JavaSerializable {
    val firstname: String get() = sportsmanSensorUI.name
    val lastname: String get() = sportsmanSensorUI.lastname

    val chss: Int
        get() = sportsmanSensorUI.sensor?.heartRate?.lastOrNull()?.value.orEmpty()

    val status: TestStatus
        get() {
            return if (sportsmanSensorUI.isTraining) {
                Running
            } else {
                TestEnd
            }
        }
}

sealed class TestStatus(val action: StringResource, val color: Color) : JavaSerializable {
    class Chill(val timer: Long = 120) :
        TestStatus(action = Res.string.chill, color = Color(0xFF8BADD1)), JavaSerializable

    object TestEnd : TestStatus(action = Res.string.test_end, color = Color(0xFF808080)),
        JavaSerializable

    object Running :
        TestStatus(action = Res.string.end, color = Color(0xFF83AF4D).copy(alpha = 0.8f)),
        JavaSerializable

    object LyingPosition :
        TestStatus(action = Res.string.lying_position, color = Color(0xFFD45470)), JavaSerializable

    object StandingPosition :
        TestStatus(action = Res.string.standing_position, color = Color(0xFF669FDA)),
        JavaSerializable


}