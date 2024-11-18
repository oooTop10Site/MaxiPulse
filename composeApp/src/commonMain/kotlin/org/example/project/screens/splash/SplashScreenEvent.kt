package org.example.project.screens.splash

sealed interface SplashScreenEvent {
    object Success: SplashScreenEvent
    object Failure: SplashScreenEvent
}