package org.example.project.screens.loginScreen

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class LoginViewModel : BaseScreenModel<LoginState, LoginEvent>(LoginState.InitState) {
    fun changeRememberMe() = intent() {
        reduce {
            state.copy(
                rememberMe = !state.rememberMe
            )
        }
    }

    fun changeShowPassword() = intent {
        reduce {
            state.copy(
                isShowPassword = !state.isShowPassword
            )
        }
    }

    @OptIn(OrbitExperimental::class)
    fun changeLogin(value: String) = blockingIntent() {
        reduce {
            state.copy(login = value)
        }
    }

    @OptIn(OrbitExperimental::class)
    fun changePassword(value: String) = blockingIntent() {
        reduce {
            state.copy(password = value)
        }
    }

    fun login() = intent {
        postSideEffect(LoginEvent.Success)
    }
}