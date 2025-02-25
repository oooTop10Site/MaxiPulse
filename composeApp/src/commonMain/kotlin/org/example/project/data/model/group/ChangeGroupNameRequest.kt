package org.example.project.data.model.group

import kotlinx.serialization.Serializable


@Serializable
class ChangeGroupNameRequest(
    val name: String
)