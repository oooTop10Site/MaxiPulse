package org.example.project.screens.adaptive.splash

sealed interface SplashScreenEvent {
    object Success: SplashScreenEvent
    object Failure: SplashScreenEvent
}