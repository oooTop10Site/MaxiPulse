package org.example.project.data.mapper

import org.example.project.data.model.rank.RankResponse
import org.example.project.domain.model.rank.RankUI

fun RankResponse.toUI(): RankUI {
    return RankUI(
        id.orEmpty(), gameTypeId.orEmpty(), name.orEmpty()
    )
}