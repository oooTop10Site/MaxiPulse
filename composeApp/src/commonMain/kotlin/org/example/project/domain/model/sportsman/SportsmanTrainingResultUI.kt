package org.example.project.domain.model.sportsman

data class SportsmanTrainingResultUI(
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
    val heartRateAvg: Int,
    val time: Long, //seconds
    val zone1: Long, // seconds
    val zone2: Long, // seconds
    val zone3: Long, // seconds
    val zone4: Long, // seconds
    val zone5: Long, // seconds
)