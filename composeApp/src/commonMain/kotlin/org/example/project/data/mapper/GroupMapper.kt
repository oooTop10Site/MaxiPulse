package org.example.project.data.mapper

import org.example.project.data.model.group.GroupResponse
import org.example.project.domain.model.composition.GroupUI
import org.example.project.utils.orEmpty

fun GroupResponse.toUI(): GroupUI {
    return GroupUI(
        id = id.orEmpty(),
        title = title.orEmpty(),
        avatar = image.orEmpty(),
        member = count.orEmpty(),
        yearReadies = "",
        selectTrainingStage = "" //todo()
    )
}