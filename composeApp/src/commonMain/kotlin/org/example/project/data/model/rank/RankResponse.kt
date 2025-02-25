package org.example.project.data.model.rank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class RankResponse(
    val id: String?,
    @SerialName("game_type_id")
    val gameTypeId: String?,
    val name: String?,
)