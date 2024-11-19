package org.example.project.screens.sportsman.edit

sealed interface SportsmanEditEvent {
    object Save: SportsmanEditEvent
}