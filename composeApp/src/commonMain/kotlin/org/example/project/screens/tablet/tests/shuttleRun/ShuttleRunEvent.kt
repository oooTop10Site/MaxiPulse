package org.example.project.screens.tablet.tests.shuttleRun

sealed interface ShuttleRunEvent {

    object StopShuttleRun: ShuttleRunEvent
}