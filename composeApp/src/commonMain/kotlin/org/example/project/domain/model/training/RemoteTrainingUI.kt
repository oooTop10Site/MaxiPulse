package org.example.project.domain.model.training

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.ended
import maxipuls.composeapp.generated.resources.in_process
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.platform.randomUUID
import org.jetbrains.compose.resources.StringResource

data class RemoteTrainingUI(
    val id: String,
    val title: String,
    val desc: String,
    val localDateTime: LocalDateTime,
    val members: List<RemoteTrainingSportsmanUI>,
    val status: RemoteTrainingStatus
) {
    companion object {
        val Default = RemoteTrainingUI(
            id = randomUUID(),
            title = "",
            desc = "",
            localDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            members = emptyList(),
            status = RemoteTrainingStatus.InProcess
        )
    }
}

data class RemoteTrainingSportsmanUI(
    val sportsmanUI: SportsmanUI,
    val status: RemoteTrainingSportsmanStatus
)

enum class RemoteTrainingSportsmanStatus(
    val title: StringResource,
    val color: Color,
) {
    InProcess(title = Res.string.in_process, color = Color(0xFFE81F61)),
    End(title = Res.string.ended, color = Color(0xFF3093F9))
}

enum class RemoteTrainingStatus(
    val title: StringResource,
    val color: Color,
) {
    InProcess(title = Res.string.in_process, color = Color(0xFFF59CB9)),
    End(title = Res.string.ended, color = Color(0xFF3093F9))
}

