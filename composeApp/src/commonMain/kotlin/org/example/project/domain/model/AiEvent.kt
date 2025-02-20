package org.example.project.domain.model

import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.training.TrainingStageChssUI

sealed class AiEvent(val prefix: String) {
    class ScreenEvent(val value: Screens) : AiEvent(prefix = AiEventEnum.ScreenEvent.prefix)
    class TrainingEvent(val value: List<TrainingStageChssUI>) :
        AiEvent(prefix = AiEventEnum.TrainingEvent.prefix)

    object Unknown : AiEvent(prefix = AiEventEnum.Unknown.prefix)
}

enum class AiEventEnum(val prefix: String) {
    ScreenEvent("screen_event"),
    TrainingEvent("training_event"),
    Unknown("unknown")
}