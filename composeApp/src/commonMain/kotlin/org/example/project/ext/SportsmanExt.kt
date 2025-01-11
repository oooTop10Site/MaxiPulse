package org.example.project.ext

import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanUI

fun SportsmanUI.toSensorUI(): SportsmanSensorUI {
    return SportsmanSensorUI(
        number = number.toString(),
        sensor = null,
        name = name,
        middleName = middleName,
        lastname = lastname,
        compositionNumber = groupId,
        id = id
    )
}