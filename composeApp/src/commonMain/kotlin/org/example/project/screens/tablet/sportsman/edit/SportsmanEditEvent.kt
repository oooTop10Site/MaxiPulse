package org.example.project.screens.tablet.sportsman.edit

sealed interface SportsmanEditEvent {
    object Save: SportsmanEditEvent
    object Delete: SportsmanEditEvent
}