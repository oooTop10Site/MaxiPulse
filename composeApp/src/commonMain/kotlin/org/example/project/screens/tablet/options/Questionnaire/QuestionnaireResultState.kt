package org.example.project.screens.tablet.options.Questionnaire

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.questionnaire.Questionnaire
import org.example.project.domain.model.questionnaire.SportsmanQuestionnaireUI

data class QuestionnaireResultState(
    val currentType: Questionnaire,
    val types: List<Questionnaire>,
    val sportsmans: List<SportsmanQuestionnaireUI>,
    val selectGroup: String?,
    val groups: List<String>,
    val selectSportsman: SportsmanQuestionnaireUI?,
    val date: LocalDate?
) {
    companion object {
        val InitState = QuestionnaireResultState(
            currentType = Questionnaire.ScaleTampa,
            types = Questionnaire.entries,
            sportsmans = listOf(
                SportsmanQuestionnaireUI(
                    firstname = "Иван",
                    lastname = "Иванов",
                    middlename = "Иванович",
                    fear = 75,
                    avoid = 60,
                    anxiety = 80,
                    date = LocalDate(2024, 2, 6)
                ),
                SportsmanQuestionnaireUI(
                    firstname = "Петр",
                    lastname = "Петров",
                    middlename = "Петрович",
                    fear = 50,
                    avoid = 45,
                    anxiety = 55,
                    date = LocalDate(2024, 1, 15)
                ),
                SportsmanQuestionnaireUI(
                    firstname = "Алексей",
                    lastname = "Сидоров",
                    middlename = "Алексеевич",
                    fear = 30,
                    avoid = 25,
                    anxiety = 40,
                    date = LocalDate(2023, 12, 10)
                )
            ),
            selectGroup = "",
            groups = emptyList(),
            selectSportsman = null,
            date = null
        )
    }
}