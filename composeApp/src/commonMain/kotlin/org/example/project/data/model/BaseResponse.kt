package org.example.project.data.model

import kotlinx.serialization.Serializable

@Serializable
class BaseResponse<T>(
    val message: String,
    val data: T?
)