package org.example.project.data.mapper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDate
import org.example.project.data.model.sportsman.GamerResponse
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI
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
        isMale = isMale == true,
        chssMax = chssMax.orEmpty(),
        chssPao = chssPao.orEmpty(),
        chssPano = chssPano.orEmpty(),
        chssResting = chssDefault.orEmpty(),
        mpk = mpk?.toIntOrNull().orEmpty(),
        imt = imt?.toDoubleOrNull().orEmpty(),
        birthDay = birthday?.localDate(),
        gameTypeId = gameTypeId.orEmpty(),
        groupId = "", //todo
        rankId = rankId.orEmpty(),
        trainigStageId = trainingStageId.orEmpty(),
    )
}

fun String.localDate(): LocalDate {
    val localDate = this.split("-")
    return LocalDate(
        localDate.getOrNull(0)?.toIntOrNull() ?: 1970,
        localDate.getOrNull(1)?.toIntOrNull() ?: 1,
        localDate.getOrNull(2)?.toIntOrNull() ?: 1,
    )
}