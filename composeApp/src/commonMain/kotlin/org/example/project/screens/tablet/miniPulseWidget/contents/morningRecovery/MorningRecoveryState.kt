package org.example.project.screens.tablet.miniPulseWidget.contents.morningRecovery

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.domain.model.test.TestResultStatus
import org.example.project.ext.getCurrentWeekDates

data class MorningRecoveryState(
    val selectDate: LocalDate,
    val currentWeek: List<LocalDate>,
    val users: List<SportsmanTestResultUI>,
    val filterUsers: List<SportsmanTestResultUI>,
    val selectSportsman: List<SportsmanTestResultUI>?,
    val isAlertDialog: Boolean,
) {

    companion object {
        val InitState = MorningRecoveryState(
            selectDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            users = listOf(
                // Иван Петров (sportsmanId = "101"), возраст 25
                SportsmanTestResultUI(
                    id = "2",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 70,
                    date = LocalDate(2025, 3, 10),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "3",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 50,
                    date = LocalDate(2025, 3, 11),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "8",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 90,
                    date = LocalDate(2025, 3, 12),
                    status = TestResultStatus.Good
                ),
                SportsmanTestResultUI(
                    id = "9",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 60,
                    date = LocalDate(2025, 3, 13),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "10",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 75,
                    date = LocalDate(2025, 3, 14),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "11",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 95,
                    date = LocalDate(2025, 3, 15),
                    status = TestResultStatus.Good
                ),
                SportsmanTestResultUI(
                    id = "22",
                    sportsmanId = "101",
                    image = "",
                    name = "Иван",
                    lastname = "Петров",
                    age = 25,
                    value = 85,
                    date = LocalDate(2025, 3, 16),
                    status = TestResultStatus.Good
                ),

                // Алексей Смирнов (sportsmanId = "102"), возраст 27
                SportsmanTestResultUI(
                    id = "5",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 65,
                    date = LocalDate(2025, 3, 10),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "12",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 45,
                    date = LocalDate(2025, 3, 11),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "13",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 80,
                    date = LocalDate(2025, 3, 12),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "14",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 20,
                    date = LocalDate(2025, 3, 13),
                    status = TestResultStatus.VeryBad
                ),
                SportsmanTestResultUI(
                    id = "15",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 70,
                    date = LocalDate(2025, 3, 14),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "16",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 85,
                    date = LocalDate(2025, 3, 15),
                    status = TestResultStatus.Good
                ),
                SportsmanTestResultUI(
                    id = "23",
                    sportsmanId = "102",
                    image = "",
                    name = "Алексей",
                    lastname = "Смирнов",
                    age = 27,
                    value = 35,
                    date = LocalDate(2025, 3, 16),
                    status = TestResultStatus.VeryBad
                ),

                // Дмитрий Кузнецов (sportsmanId = "103"), возраст 23
                SportsmanTestResultUI(
                    id = "7",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 40,
                    date = LocalDate(2025, 3, 10),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "17",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 60,
                    date = LocalDate(2025, 3, 11),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "18",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 75,
                    date = LocalDate(2025, 3, 12),
                    status = TestResultStatus.Normal
                ),
                SportsmanTestResultUI(
                    id = "19",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 90,
                    date = LocalDate(2025, 3, 13),
                    status = TestResultStatus.Good
                ),
                SportsmanTestResultUI(
                    id = "20",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 55,
                    date = LocalDate(2025, 3, 14),
                    status = TestResultStatus.Bad
                ),
                SportsmanTestResultUI(
                    id = "21",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 100,
                    date = LocalDate(2025, 3, 15),
                    status = TestResultStatus.Good
                ),
                SportsmanTestResultUI(
                    id = "24",
                    sportsmanId = "103",
                    image = "",
                    name = "Дмитрий",
                    lastname = "Кузнецов",
                    age = 23,
                    value = 70,
                    date = LocalDate(2025, 3, 16),
                    status = TestResultStatus.Normal
                )
            ),
            filterUsers = emptyList(),
            currentWeek = getCurrentWeekDates(),
            selectSportsman = null,
            isAlertDialog = false
        )
    }
}