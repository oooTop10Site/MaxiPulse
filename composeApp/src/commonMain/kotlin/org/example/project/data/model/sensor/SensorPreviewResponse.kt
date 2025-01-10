package org.example.project.data.model.sensor

import kotlinx.serialization.Serializable


@Serializable
class SensorPreviewResponse(
    val id: String?,
    val name: String?,
    val mac: String?,
)