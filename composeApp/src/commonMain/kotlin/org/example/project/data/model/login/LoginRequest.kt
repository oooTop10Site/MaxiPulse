package org.example.project.data.model.login

import kotlinx.serialization.Serializable


@Serializable
class LoginRequest (
    val login: String,
    val password: String
)