package org.example.project.data.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class PromptResponse(
    @SerialName("generated_text")
    val generatedText: String?
)