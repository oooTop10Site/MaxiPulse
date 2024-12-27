package org.example.project.screens.tablet.tests.readiesForUpload.result

import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.domain.model.test.TestResultStatus

data class ReadiesForUploadResultState(
    val filter: String,
    val filters: List<String>,
    val search: String,
    val users: List<SportsmanTestResultUI>
) {
    companion object {
        val InitState = ReadiesForUploadResultState(
            "fsf",
            listOf("fsf", "34242", "fsdfs", "fsdfsf"),
            "",
            users = listOf(
                SportsmanTestResultUI("", "John", "Doe", TestResultStatus.Good),
                SportsmanTestResultUI("", "Jane", "Smith", TestResultStatus.Normal),
                SportsmanTestResultUI("", "Michael", "Johnson", TestResultStatus.Bad),
                SportsmanTestResultUI("", "Emily", "Davis", TestResultStatus.VeryBad),
                SportsmanTestResultUI("", "Chris", "Martinez", TestResultStatus.Good),
                SportsmanTestResultUI("", "Jessica", "Garcia", TestResultStatus.Normal),
                SportsmanTestResultUI("", "David", "Rodriguez", TestResultStatus.Bad),
                SportsmanTestResultUI("", "Sarah", "Wilson", TestResultStatus.VeryBad),
                SportsmanTestResultUI("", "Robert", "Anderson", TestResultStatus.Good),
                SportsmanTestResultUI("", "Laura", "Thomas", TestResultStatus.Normal),
                SportsmanTestResultUI("", "James", "Taylor", TestResultStatus.Bad),
                SportsmanTestResultUI("", "Olivia", "Hernandez", TestResultStatus.VeryBad),
                SportsmanTestResultUI("", "Daniel", "Moore", TestResultStatus.Good),
                SportsmanTestResultUI("", "Sophia", "Martin", TestResultStatus.Normal),
                SportsmanTestResultUI("", "Matthew", "Jackson", TestResultStatus.Bad),
                SportsmanTestResultUI("", "Isabella", "Lee", TestResultStatus.VeryBad),
                SportsmanTestResultUI("", "Joshua", "Perez", TestResultStatus.Good),
                SportsmanTestResultUI("", "Mia", "Thompson", TestResultStatus.Normal),
                SportsmanTestResultUI("", "Andrew", "White", TestResultStatus.Bad),
                SportsmanTestResultUI("", "Ella", "Lopez", TestResultStatus.VeryBad)
            )
        )
    }
}