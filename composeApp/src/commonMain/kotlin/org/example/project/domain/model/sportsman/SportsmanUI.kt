package org.example.project.domain.model.sportsman

import kotlinx.datetime.LocalDate
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI

data class SportsmanUI(
    val id: String,
    val number: Int,
    val name: String,
    val lastname: String,
    val middleName: String,
    val age: Int,
    val height: Int,
    val weight: Int,
    val avatar: String,
    val isMale: Boolean,
    val mpk: Int,
    val imt: Double,
    val chssPano: Int,
    val chssPao: Int,
    val chssMax: Int,
    val chssResting: Int,
    val birthDay: LocalDate?,
    val gameTypeId: String,
    val rankId: String,
    val groupId: String,
    val trainigStageId: String
) {
    val fio: String
        get() = "$lastname $name $middleName"

    val fioShort: String
        get() = "$lastname ${name.firstOrNull()?: ""}.${middleName.firstOrNull()?: ""}."

    companion object {
        val Default = SportsmanUI(
            "", 0, "", "", "", 0, 0, 0, "", true, 0, 0.0, 0, 0, 0, 0, null, "",
            "", "", ""
        )
    }
}
