package org.example.project.ext

import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.domain.model.training.TrainingUtpStageUI

fun TrainingUtpStageUI.toTrainingStageChssUI(): TrainingStageChssUI {
    return TrainingStageChssUI(time = this.min.toLong(), title = this.title, chss = this.value)
}

fun List<TrainingStageChssUI>.toFormattedString(): String {
    return joinToString(" ") {
        "${it.title.stageNameToNumber()}-${it.time}-${it.chss}"
    }
}

fun String.stageNameToNumber(): Int? {
    return when (this.lowercase()) {
        "первый" -> 1
        "второй" -> 2
        "третий" -> 3
        "четвертый" -> 4
        "пятый" -> 5
        "шестой" -> 6
        "седьмой" -> 7
        "восьмой" -> 8
        "девятый" -> 9
        "десятый" -> 10
        else -> null // Возвращает null, если не находит соответствия
    }
}