package org.example.project.utils

import androidx.compose.ui.unit.dp

object Constants {
    const val FILE_SIZE_MB = 10
    const val BASE_URL = "https://api.maxipulse.vobrabotke.ru/"
    const val BASE_URL_AI = "https://192.168.45.175:8000/"
    const val BASE_SOCKET_URL = "ws://192.168.147.127:8000/ws"
    val TextFieldHeight = 44.dp
    const val MAX_TRAINING_INTENSITY = 200
    const val IsDataSocketFromSensor = false
    const val Debounce = 300L

    val zone1Range: List<Int> = IntRange(zone1Start, zone1End).toList()
    val zone2Range: List<Int> = IntRange(zone2Start, zone2End).toList()
    val zone3Range: List<Int> = IntRange(zone3Start, zone3End).toList()
    val zone4Range: List<Int> = IntRange(zone4Start, zone4End).toList()
    val zone5Range: List<Int> = IntRange(zone5Start, zone5End).toList()

    const val zone1Start = 0
    const val zone1End = 70
    const val zone2Start = 71
    const val zone2End = 90
    const val zone3Start = 91
    const val zone3End = 110
    const val zone4Start = 111
    const val zone4End = 130
    const val zone5Start = 131
    const val zone5End = 230

}