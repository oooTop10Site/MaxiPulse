package org.example.project.screens.tests.shuttleRun

sealed interface ShuttleRunEvent {

    object StopShuttleRun: ShuttleRunEvent
}