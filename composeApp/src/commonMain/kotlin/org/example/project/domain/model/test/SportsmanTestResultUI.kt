package org.example.project.domain.model.test

import androidx.compose.ui.graphics.Color
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.emoj1
import maxipuls.composeapp.generated.resources.emoj2
import maxipuls.composeapp.generated.resources.emoj3
import maxipuls.composeapp.generated.resources.emoj4
import org.jetbrains.compose.resources.DrawableResource

data class SportsmanTestResultUI(
    val image: String,
    val name: String,
    val lastname: String,
    val status: TestResultStatus
)

sealed class TestResultStatus(val icon: DrawableResource, val color: Color) {
    object Good: TestResultStatus(icon = Res.drawable.emoj1, color = Color(0xFF96D34B).copy(alpha = 0.4f))
    object Normal: TestResultStatus(icon = Res.drawable.emoj2, color = Color(0xFFFFCC4D).copy(alpha = 0.4f))
    object Bad: TestResultStatus(icon = Res.drawable.emoj3, color = Color(0xFFDE6430).copy(alpha = 0.4f))
    object VeryBad: TestResultStatus(icon = Res.drawable.emoj4, color = Color(0xFFB4AD9C).copy(alpha = 0.4f))
}