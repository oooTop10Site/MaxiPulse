package org.example.project.ext

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.sportsman.Performance
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.domain.model.test.TestResultStatus
import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.platform.randomUUID
import org.example.project.screens.tablet.options.utp.UtpState.Companion.currentDate
import org.example.project.utils.Constants
import org.example.project.utils.SportsmanMeasure
import org.example.project.utils.orEmpty

fun SportsmanUI.toSensorUI(): SportsmanSensorUI {
    return SportsmanSensorUI(
        number = number.toString(),
        sensor = null,
        name = name,
        middleName = middleName,
        lastname = lastname,
        compositionNumber = groupId,
        id = id,
        avatar = avatar,
        age = age,
        isMale = isMale,
    )
}

fun TestSportsmanUI.toShuttleRunTestResult(): SportsmanShuttleRunResultUI {
    val sportsmanSensorUI = this.sportsmanSensorUI
    val avgHR = sportsmanSensorUI.avgHeartRate
    val performance = when {
        avgHR > 180 -> Performance.Max
        avgHR > 140 -> Performance.Avg
        else -> Performance.Min
    }
    val heartRates = sportsmanSensorUI.sensor?.heartRate.orEmpty()
    return SportsmanShuttleRunResultUI(
        id = sportsmanSensorUI.id,
        middleName = sportsmanSensorUI.middleName,
        firstname = firstname,
        lastname = lastname,
        number = sportsmanSensorUI.number.toIntOrNull().orEmpty(),
        avatar = sportsmanSensorUI.avatar,
        age = sportsmanSensorUI.age,
        distance = 200L, //todo
        heartRateMax = sportsmanSensorUI.maxHeartRate,
        chssPao = SportsmanMeasure.calculateChssPao(
            heartRates = heartRates,
            heartRateMax = sportsmanSensorUI.maxHeartRate
        ),
        chssPano = SportsmanMeasure.calculateChssPano(
            heartRates = heartRates,
            heartRateMax = sportsmanSensorUI.maxHeartRate
        ),
        mpk = SportsmanMeasure.calculateMPK(
            maxHeartRate = sportsmanSensorUI.maxHeartRate,
            age = sportsmanSensorUI.age
        ),
        performance = performance,
        time = sportsmanSensorUI.timeTrainingSeconds
    )
}

fun TestSportsmanUI.toReadiesForUploadResult(): SportsmanTestResultUI {
    val averageHeartRate = this.sportsmanSensorUI.avgHeartRate
    return SportsmanTestResultUI(
        image = "",
        name = firstname,
        lastname = lastname,
        value = averageHeartRate,
        status = when {
            averageHeartRate <= 120 -> TestResultStatus.Good
            averageHeartRate in 121..150 -> TestResultStatus.Normal
            averageHeartRate in 151..180 -> TestResultStatus.Bad
            else -> TestResultStatus.VeryBad
        },
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        id = randomUUID(),
        sportsmanId = this.sportsmanSensorUI.id,
        age = this.sportsmanSensorUI.age
    )
}

fun TestSportsmanUI.toMorningRecovery(): SportsmanTestResultUI {
    val age = sportsmanSensorUI.age
    val heartRate = sportsmanSensorUI.sensor?.heartRate?.lastOrNull()?.value ?: 0

    val score = when (age) {
        in 6..7 -> when (heartRate) {
            in 75..94 -> 3
            in 95..98 -> 2
            else -> 1
        }

        in 8..9 -> when (heartRate) {
            in 70..91 -> 3
            in 92..95 -> 2
            else -> 1
        }

        in 10..11 -> when (heartRate) {
            in 65..88 -> 3
            in 89..92 -> 2
            else -> 1
        }

        in 12..13 -> when (heartRate) {
            in 60..85 -> 3
            in 86..90 -> 2
            else -> 1
        }

        in 14..15 -> when (heartRate) {
            in 55..80 -> 3
            in 81..85 -> 2
            else -> 1
        }

        else -> when (heartRate) {
            in 50..75 -> 3
            in 76..80 -> 2
            else -> 1
        }
    }

    val status = when (score) {
        3 -> TestResultStatus.Good
        2 -> TestResultStatus.Normal
        1 -> TestResultStatus.Bad
        else -> TestResultStatus.VeryBad
    }

    return SportsmanTestResultUI(
        image = "",
        name = firstname,
        lastname = lastname,
        value = heartRate,
        status = status,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        id = randomUUID(),
        sportsmanId = this.sportsmanSensorUI.id,
        age = this.sportsmanSensorUI.age
    )
}

fun SportsmanSensorUI.toSportsmanTrainingResultUI(): SportsmanTrainingResultUI {
    return SportsmanTrainingResultUI(
        id = id,
        number = number.toIntOrNull().orEmpty(),
        firstname = name,
        middleName = middleName,
        lastname = lastname,
        avatar = avatar,
        age = age,
        kcal = 0, //todo
        trimp = SportsmanMeasure.trimp(
            isMale = isMale,
            chssMax = heartRateMax,
            trainingTimeSeconds = timeTrainingSeconds,
            chssReasting = 50,//todo
            chssMaxOnTraining = maxHeartRate
        ),
        heartRateMax = maxHeartRate,
        heartRateMin = minHeartRate,
        heartRateAvg = avgHeartRate,
        time = timeTrainingSeconds,
        zone1 = zone1,
        zone2 = zone2,
        zone3 = zone3,
        zone4 = zone4,
        zone5 = zone5,
        heartRate = sensor?.heartRate.orEmpty()
    )
}