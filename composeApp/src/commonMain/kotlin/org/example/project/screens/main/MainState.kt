package org.example.project.screens.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isStartTraining: Boolean,
    val isActiveSensor: Boolean,
    val alertDialog: MainAlertDialog?,
    val sensors: List<SensorUI>
) {
    companion object {
        val InitState = MainState(
            sportsmans = listOf(
                SportsmanSensorUI(id = "1", number = "001", name = "John", lastname = "Doe", middleName = "A.", compositionNumber = 1, sensor = SensorUI("SENSOR_1")),
                SportsmanSensorUI(id = "2", number = "002", name = "Jane", lastname = "Smith", middleName = "B.", compositionNumber = 2, sensor = null),
                SportsmanSensorUI(id = "3", number = "003", name = "Michael", lastname = "Johnson", middleName = "C.", compositionNumber = 3, sensor = SensorUI("SENSOR_3")),
                SportsmanSensorUI(id = "4", number = "004", name = "Emily", lastname = "Davis", middleName = "D.", compositionNumber = 4, sensor = null),
                SportsmanSensorUI(id = "5", number = "005", name = "Chris", lastname = "Martinez", middleName = "E.", compositionNumber = 5, sensor = SensorUI("SENSOR_5")),
                SportsmanSensorUI(id = "6", number = "006", name = "Jessica", lastname = "Garcia", middleName = "F.", compositionNumber = 6, sensor = null),
                SportsmanSensorUI(id = "7", number = "007", name = "David", lastname = "Rodriguez", middleName = "G.", compositionNumber = 7, sensor = null),
                SportsmanSensorUI(id = "8", number = "008", name = "Sarah", lastname = "Wilson", middleName = "H.", compositionNumber = 8, sensor = SensorUI("SENSOR_8")),
                SportsmanSensorUI(id = "9", number = "009", name = "Robert", lastname = "Anderson", middleName = "I.", compositionNumber = 9, sensor = SensorUI("SENSOR_9")),
                SportsmanSensorUI(id = "10", number = "010", name = "Laura", lastname = "Thomas", middleName = "J.", compositionNumber = 10, sensor = null),
                SportsmanSensorUI(id = "11", number = "011", name = "James", lastname = "Taylor", middleName = "K.", compositionNumber = 11, sensor = SensorUI("SENSOR_11")),
                SportsmanSensorUI(id = "12", number = "012", name = "Olivia", lastname = "Hernandez", middleName = "L.", compositionNumber = 12, sensor = null),
                SportsmanSensorUI(id = "13", number = "013", name = "Daniel", lastname = "Moore", middleName = "M.", compositionNumber = 13, sensor = SensorUI("SENSOR_13")),
                SportsmanSensorUI(id = "14", number = "014", name = "Sophia", lastname = "Martin", middleName = "N.", compositionNumber = 14, sensor = null),
                SportsmanSensorUI(id = "15", number = "015", name = "Matthew", lastname = "Jackson", middleName = "O.", compositionNumber = 15, sensor = null),
                SportsmanSensorUI(id = "16", number = "016", name = "Isabella", lastname = "Lee", middleName = "P.", compositionNumber = 16, sensor = null),
                SportsmanSensorUI(id = "17", number = "017", name = "Joshua", lastname = "Perez", middleName = "Q.", compositionNumber = 17, sensor = SensorUI("SENSOR_17")),
                SportsmanSensorUI(id = "18", number = "018", name = "Mia", lastname = "Thompson", middleName = "R.", compositionNumber = 18, sensor = null),
                SportsmanSensorUI(id = "19", number = "019", name = "Andrew", lastname = "White", middleName = "S.", compositionNumber = 19, sensor = SensorUI("SENSOR_19")),
                SportsmanSensorUI(id = "20", number = "020", name = "Ella", lastname = "Lopez", middleName = "T.", compositionNumber = 20, sensor = null),
                SportsmanSensorUI(id = "21", number = "021", name = "Ryan", lastname = "Gonzalez", middleName = "U.", compositionNumber = 21, sensor = SensorUI("SENSOR_21")),
                SportsmanSensorUI(id = "22", number = "022", name = "Lily", lastname = "Clark", middleName = "V.", compositionNumber = 22, sensor = null),
                SportsmanSensorUI(id = "23", number = "023", name = "Brandon", lastname = "Lewis", middleName = "W.", compositionNumber = 23, sensor = SensorUI("SENSOR_23")),
                SportsmanSensorUI(id = "24", number = "024", name = "Ava", lastname = "Walker", middleName = "X.", compositionNumber = 24, sensor = null),
                SportsmanSensorUI(id = "25", number = "025", name = "Ethan", lastname = "Young", middleName = "Y.", compositionNumber = 25, sensor = SensorUI("SENSOR_25"))
            ),
            selectSportsmans = emptyList(),
            search = "",
            isStartTraining = false,
            isActiveSensor = false,
            alertDialog = null,
            sensors = listOf(
                SensorUI("SENSOR_1"),
                SensorUI("SENSOR_3"),
                SensorUI("SENSOR_5"),
                SensorUI("SENSOR_8"),
                SensorUI("SENSOR_9"),
                SensorUI("SENSOR_11"),
                SensorUI("SENSOR_13"),
                SensorUI("SENSOR_17"),
                SensorUI("SENSOR_19"),
                SensorUI("SENSOR_21"),
                SensorUI("SENSOR_23"),
                SensorUI("SENSOR_25"),
                SensorUI("SENSOR_26"),
                SensorUI("SENSOR_27"),
                SensorUI("SENSOR_28"),
                SensorUI("SENSOR_29"),
                SensorUI("SENSOR_30"),
                SensorUI("SENSOR_31"),
            )
        )
    }
}
