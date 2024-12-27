package org.example.project.screens.adaptive.login

data class LoginState(
    val login: String,
    val password: String,
    val rememberMe: Boolean,
    val isShowPassword: Boolean
) {
    companion object {
        val InitState = LoginState(
            login = "",
            password = "",
            rememberMe = true,
            isShowPassword = false
        )
    }
}