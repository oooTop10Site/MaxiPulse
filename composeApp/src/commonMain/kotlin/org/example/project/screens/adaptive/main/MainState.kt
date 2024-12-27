package org.example.project.screens.adaptive.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.task.MainTaskUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isStartTraining: Boolean,
    val isActiveSensor: Boolean,
    val alertDialog: MainAlertDialog?,
    val sensors: List<SensorUI>,
    //mobile
    val avatar: String,
    val task: List<MainTaskUI>,
    val currentTask: MainTaskUI,
    val name: String,
    val lastname: String,
    val middleName: String,
) {
    companion object {
        val InitState = MainState(
            sportsmans = listOf(
                SportsmanSensorUI(
                    id = "1",
                    number = "001",
                    name = "John",
                    lastname = "Doe",
                    middleName = "A.",
                    compositionNumber = 1,
                    sensor = SensorUI("SENSOR_1", "Device_1", SensorStatus.Active)
                ),
                SportsmanSensorUI(
                    id = "2",
                    number = "002",
                    name = "Jane",
                    lastname = "Smith",
                    middleName = "B.",
                    compositionNumber = 2,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "3",
                    number = "003",
                    name = "Michael",
                    lastname = "Johnson",
                    middleName = "C.",
                    compositionNumber = 3,
                    sensor = SensorUI("SENSOR_3", "Device_3", SensorStatus.Unknown)
                ),
                SportsmanSensorUI(
                    id = "4",
                    number = "004",
                    name = "Emily",
                    lastname = "Davis",
                    middleName = "D.",
                    compositionNumber = 4,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "5",
                    number = "005",
                    name = "Chris",
                    lastname = "Martinez",
                    middleName = "E.",
                    compositionNumber = 5,
                    sensor = SensorUI("SENSOR_5", "Device_5", SensorStatus.Disable)
                ),
                SportsmanSensorUI(
                    id = "6",
                    number = "006",
                    name = "Jessica",
                    lastname = "Garcia",
                    middleName = "F.",
                    compositionNumber = 6,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "7",
                    number = "007",
                    name = "David",
                    lastname = "Rodriguez",
                    middleName = "G.",
                    compositionNumber = 7,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "8",
                    number = "008",
                    name = "Sarah",
                    lastname = "Wilson",
                    middleName = "H.",
                    compositionNumber = 8,
                    sensor = SensorUI("SENSOR_8", "Device_8", SensorStatus.Active)
                ),
                SportsmanSensorUI(
                    id = "9",
                    number = "009",
                    name = "Robert",
                    lastname = "Anderson",
                    middleName = "I.",
                    compositionNumber = 9,
                    sensor = SensorUI("SENSOR_9", "Device_9", SensorStatus.Disable)
                ),
                SportsmanSensorUI(
                    id = "10",
                    number = "010",
                    name = "Laura",
                    lastname = "Thomas",
                    middleName = "J.",
                    compositionNumber = 10,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "11",
                    number = "011",
                    name = "James",
                    lastname = "Taylor",
                    middleName = "K.",
                    compositionNumber = 11,
                    sensor = SensorUI("SENSOR_11", "Device_11", SensorStatus.Unknown)
                ),
                SportsmanSensorUI(
                    id = "12",
                    number = "012",
                    name = "Olivia",
                    lastname = "Hernandez",
                    middleName = "L.",
                    compositionNumber = 12,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "13",
                    number = "013",
                    name = "Daniel",
                    lastname = "Moore",
                    middleName = "M.",
                    compositionNumber = 13,
                    sensor = SensorUI("SENSOR_13", "Device_13", SensorStatus.Active)
                ),
                SportsmanSensorUI(
                    id = "14",
                    number = "014",
                    name = "Sophia",
                    lastname = "Martin",
                    middleName = "N.",
                    compositionNumber = 14,
                    sensor = null
                ),
                SportsmanSensorUI(
                    id = "15",
                    number = "015",
                    name = "Matthew",
                    lastname = "Jackson",
                    middleName = "O.",
                    compositionNumber = 15,
                    sensor = null
                )
            ),
            selectSportsmans = emptyList(),
            search = "",
            isStartTraining = false,
            isActiveSensor = false,
            alertDialog = null,
            sensors = listOf(

            ),
            avatar = "",
            name = "Иван",
            lastname = "Иванов",
            middleName = "Иванович",
            task = listOf(
                MainTaskUI.BorgScale,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
            ),
            currentTask = MainTaskUI.GoodMorning
        )
    }
}
