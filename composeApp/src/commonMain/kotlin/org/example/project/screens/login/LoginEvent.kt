package org.example.project.screens.login

sealed interface LoginEvent {
    object Success: LoginEvent
}