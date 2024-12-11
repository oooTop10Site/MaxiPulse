package org.example.project.domain.model.sportsman

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.average
import maxipuls.composeapp.generated.resources.high
import maxipuls.composeapp.generated.resources.minimal
import org.jetbrains.compose.resources.StringResource

data class SportsmanShuttleRunResultUI(
    val id: String,
    val number: Int,
    val firstname: String,
    val lastname: String,
    val middleName: String,
    val avatar: String,
    val age: Int,
    val distance: Long, //metrs
    val heartRateMax: Int,
    val chssPano: Int,
    val chssPao: Int,
    val mpk: Int,
    val performance: Performance,
) {
    val fio: String
        get() = "$lastname $firstname $middleName"
}

enum class Performance(val text: StringResource) {
    Min(text = Res.string.minimal),
    Max(text = Res.string.high),
    Avg(text = Res.string.average),
}