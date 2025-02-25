package org.example.project.screens.adaptive.splash

import org.example.project.domain.manager.AuthManager
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import kotlin.getValue

internal class SplashScreenViewModel: BaseScreenModel<Unit, SplashScreenEvent>(Unit) {
    val authManager: AuthManager by inject()

    init {
        intent {
            println("authManager.token - ${authManager.token}")
            if(authManager.token.isNullOrBlank()) {
                postSideEffect(SplashScreenEvent.Failure)
            } else {
                postSideEffect(SplashScreenEvent.Success)
            }
        }
    }

}