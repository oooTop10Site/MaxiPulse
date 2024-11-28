package org.example.project.data.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.data.model.sportsman.GamerResponse


@Serializable
class GroupResponse(
    @SerialName("id") val id: String?,
    @SerialName("name") val title: String?,
    @SerialName("image") val image: String?,
    @SerialName("count") val count: Int?,
    @SerialName("gamers") val gamers: List<GamerResponse>?
)