package org.example.project.data.mapper

import org.example.project.data.model.gameType.GameTypeResponse
import org.example.project.domain.model.gameType.GameTypeUI

fun GameTypeResponse.toUI(): GameTypeUI {
    return GameTypeUI(
        id.orEmpty(), name.orEmpty()
    )
}