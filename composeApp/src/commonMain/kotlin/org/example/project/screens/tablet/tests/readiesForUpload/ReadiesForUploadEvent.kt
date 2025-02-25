package org.example.project.screens.tablet.tests.readiesForUpload

import org.example.project.domain.model.test.SportsmanTestResultUI

sealed interface ReadiesForUploadEvent {
    data class Result(val sportmans: List<SportsmanTestResultUI>): ReadiesForUploadEvent
}