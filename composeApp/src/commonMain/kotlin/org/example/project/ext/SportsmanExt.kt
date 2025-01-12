package org.example.project.ext

import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.domain.model.sportsman.SportsmanUI
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
            chssMaxOnTraining = sensor?.heartRate.orEmpty().maxOf { it.value }
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