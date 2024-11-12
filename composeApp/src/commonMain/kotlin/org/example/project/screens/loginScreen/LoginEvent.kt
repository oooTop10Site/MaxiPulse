package org.example.project.screens.loginScreen

sealed interface LoginEvent {
    object Success: LoginEvent
}