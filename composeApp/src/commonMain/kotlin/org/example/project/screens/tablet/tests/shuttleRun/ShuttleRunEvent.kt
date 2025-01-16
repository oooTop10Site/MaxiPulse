package org.example.project.screens.tablet.tests.shuttleRun

import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI

sealed interface ShuttleRunEvent {

    data class StopShuttleRun(val sportsman: List<SportsmanShuttleRunResultUI>): ShuttleRunEvent
}