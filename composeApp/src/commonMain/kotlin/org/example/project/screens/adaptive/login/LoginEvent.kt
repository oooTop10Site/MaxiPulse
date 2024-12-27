package org.example.project.screens.adaptive.login

sealed interface LoginEvent {
    object Success: LoginEvent
}