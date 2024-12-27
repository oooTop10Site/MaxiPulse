package org.example.project.screens.adaptive.main

sealed interface MainEvent {
    object ShuttleRun: MainEvent
    object ReadiesForUpload: MainEvent
    object Training: MainEvent
}