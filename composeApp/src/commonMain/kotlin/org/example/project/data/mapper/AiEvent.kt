package org.example.project.data.mapper

import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.AiEvent
import org.example.project.domain.model.AiEventEnum
import org.example.project.domain.model.training.TrainingStageChssUI

fun String.toAiEvent(): AiEvent {
    return try {
        val type = this.split(" ").firstOrNull().orEmpty()
        when (type) {
            AiEventEnum.TrainingEvent.prefix -> {
                val value = this.split(" ").let { it.subList(1, it.lastIndex+1) }
                println("value = $value")
                AiEvent.TrainingEvent(value = value.map {
                    val (title, time, chss) = it.split("-")
                    TrainingStageChssUI(
                        title = title,
                        time = time.toLongOrNull() ?: 0L,
                        chss = chss.toIntOrNull() ?: 0
                    )
                })
            }

            AiEventEnum.ScreenEvent.prefix -> {
                val screen = this.split(" ").lastOrNull().orEmpty()
                Screens.entries.find { it.title == screen }?.let {
                    AiEvent.ScreenEvent(it)
                } ?: AiEvent.Unknown
            }

            else -> {
                AiEvent.Unknown
            }
        }
    } catch (e: Exception) {
        AiEvent.Unknown
    }
}