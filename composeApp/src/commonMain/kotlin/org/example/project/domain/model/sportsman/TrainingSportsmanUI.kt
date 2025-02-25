package org.example.project.domain.model.sportsman

import androidx.compose.ui.graphics.Color

data class TrainingSportsmanUI(
    val id: String,
    val number: Int,
    val firstname: String,
    val lastname: String,
    val middleName: String,
    val avatar: String,
    val age: Int,
    val kcal: Int,
    val trimp: Int,
    val heartRateMax: Int,
    val heartRateMin: Int,
    val heartBits: List<HeartBit>,
    val heartRateCurrent: Int,
    val isTraining: Boolean,
    val color: Color,
    val zone1: Long, //seconds
    val zone2: Long, //seconds
    val zone3: Long, //seconds
    val zone4: Long, //seconds
    val zone5: Long, //seconds
) {
    val fio: String
        get() = "$lastname $firstname $middleName"
}
