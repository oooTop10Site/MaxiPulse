package org.example.project.screens.main

sealed interface MainEvent {
    object ShuttleRun: MainEvent
    object ReadiesForUpload: MainEvent
    object Training: MainEvent
}