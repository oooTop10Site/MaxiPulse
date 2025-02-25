package org.example.project.screens.adaptive.login

import org.example.project.domain.manager.AuthManager
import org.example.project.domain.repository.AuthRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class LoginViewModel : BaseScreenModel<LoginState, LoginEvent>(LoginState.InitState) {
    private val authRepository: AuthRepository by inject()
    private val authManager: AuthManager by inject()
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
        launchOperation(
            operation = {
                authRepository.login(state.login, state.password)
            },
            success = {
                if(state.rememberMe) {
                    println("я тут - $it")
                    authManager.token = it
                }
                println(" authManager.token = $it")
                postSideEffectLocal(LoginEvent.Success)
            },
            failure = {

            }
        )
    }
}