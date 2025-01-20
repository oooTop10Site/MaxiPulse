package org.example.project.screens.tablet.tests.readiesForUpload.result

import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.domain.model.test.TestResultStatus

data class ReadiesForUploadResultState(
    val filter: String,
    val filters: List<String>,
    val search: String,
    val users: List<SportsmanTestResultUI>,
    val filterSportsmans: List<SportsmanTestResultUI>
) {
    companion object {
        val InitState = ReadiesForUploadResultState(
            "fsf",
            listOf("fsf", "34242", "fsdfs", "fsdfsf"),
            "",
            users = emptyList(),
            filterSportsmans = emptyList()
        )
    }
}