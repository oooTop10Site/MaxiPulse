package org.example.project.data.model.login

import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    val userId: String?,
    val token: String?
)