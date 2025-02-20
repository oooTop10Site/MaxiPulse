package org.example.project.data.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class PromptRequest(
    @SerialName("input_text")
    val inputText: String
)