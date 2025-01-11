package org.example.project.domain.model.sportsman

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class SportsmanSensorUI(
    val id: String,
    val number: String,
    val name: String,
    val lastname: String,
    val middleName: String,
    val compositionNumber: String,
    val sensor: SensorUI?,
    val avatar: String,
    val age: Int,
    val heartRateMax: Int = 230,
    val heartRateMin: Int = 0,
    val isTraining: Boolean = false,
) : JavaSerializable {

    val zone1: Long
        get() {
            return 100
        }

    val zone2: Long
        get() {
            return 100
        }

    val zone3: Long
        get() {
            return 100
        }
    val zone4: Long
        get() {
            return 100
        }
    val zone5: Long
        get() {
            return 100
        }

}