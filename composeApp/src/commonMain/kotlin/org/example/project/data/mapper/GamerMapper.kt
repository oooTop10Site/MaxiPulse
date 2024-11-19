package org.example.project.data.mapper

import org.example.project.data.model.sportsman.GamerResponse
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.utils.orEmpty
import org.koin.core.qualifier.named

fun GamerResponse.toUI(): SportsmanUI {
    return SportsmanUI(
        id = id.orEmpty(),
        weight = weight?.toIntOrNull().orEmpty(),
        middleName = middlename.orEmpty(),
        name = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        age = age.orEmpty(),
        height = height?.toIntOrNull().orEmpty(),
        number = number.orEmpty(),
        avatar = image.orEmpty(),
        isMale = isMale == true
    )
}