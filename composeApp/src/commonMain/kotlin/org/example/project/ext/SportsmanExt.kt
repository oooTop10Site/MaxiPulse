package org.example.project.ext

import org.example.project.domain.model.sportsman.Performance
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.domain.model.test.TestResultStatus
import org.example.project.domain.model.test.TestSportsmanUI
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
        status = when {
            averageHeartRate <= 120 -> TestResultStatus.Good
            averageHeartRate in 121..150 -> TestResultStatus.Normal
            averageHeartRate in 151..180 -> TestResultStatus.Bad
            else -> TestResultStatus.VeryBad
        }
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