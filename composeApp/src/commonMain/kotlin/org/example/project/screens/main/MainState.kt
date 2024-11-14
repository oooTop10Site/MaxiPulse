package org.example.project.screens.main

import org.example.project.domain.model.sportsman.SportsmanSensorUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isActiveSensor: Boolean
) {
    companion object {
        val InitState = MainState(
            listOf(
                SportsmanSensorUI(id = "1", number = "001", name = "John", lastname = "Doe", middleName = "A.", compositionNumber = 1, sensorId = "SENSOR_1"),
                SportsmanSensorUI(id = "2", number = "002", name = "Jane", lastname = "Smith", middleName = "B.", compositionNumber = 2, sensorId = ""),
                SportsmanSensorUI(id = "3", number = "003", name = "Michael", lastname = "Johnson", middleName = "C.", compositionNumber = 3, sensorId = "SENSOR_3"),
                SportsmanSensorUI(id = "4", number = "004", name = "Emily", lastname = "Davis", middleName = "D.", compositionNumber = 4, sensorId = ""),
                SportsmanSensorUI(id = "5", number = "005", name = "Chris", lastname = "Martinez", middleName = "E.", compositionNumber = 5, sensorId = "SENSOR_5"),
                SportsmanSensorUI(id = "6", number = "006", name = "Jessica", lastname = "Garcia", middleName = "F.", compositionNumber = 6, sensorId = ""),
                SportsmanSensorUI(id = "7", number = "007", name = "David", lastname = "Rodriguez", middleName = "G.", compositionNumber = 7, sensorId = ""),
                SportsmanSensorUI(id = "8", number = "008", name = "Sarah", lastname = "Wilson", middleName = "H.", compositionNumber = 8, sensorId = "SENSOR_8"),
                SportsmanSensorUI(id = "9", number = "009", name = "Robert", lastname = "Anderson", middleName = "I.", compositionNumber = 9, sensorId = "SENSOR_9"),
                SportsmanSensorUI(id = "10", number = "010", name = "Laura", lastname = "Thomas", middleName = "J.", compositionNumber = 10, sensorId = ""),
                SportsmanSensorUI(id = "11", number = "011", name = "James", lastname = "Taylor", middleName = "K.", compositionNumber = 11, sensorId = "SENSOR_11"),
                SportsmanSensorUI(id = "12", number = "012", name = "Olivia", lastname = "Hernandez", middleName = "L.", compositionNumber = 12, sensorId = ""),
                SportsmanSensorUI(id = "13", number = "013", name = "Daniel", lastname = "Moore", middleName = "M.", compositionNumber = 13, sensorId = "SENSOR_13"),
                SportsmanSensorUI(id = "14", number = "014", name = "Sophia", lastname = "Martin", middleName = "N.", compositionNumber = 14, sensorId = ""),
                SportsmanSensorUI(id = "15", number = "015", name = "Matthew", lastname = "Jackson", middleName = "O.", compositionNumber = 15, sensorId = ""),
                SportsmanSensorUI(id = "16", number = "016", name = "Isabella", lastname = "Lee", middleName = "P.", compositionNumber = 16, sensorId = ""),
                SportsmanSensorUI(id = "17", number = "017", name = "Joshua", lastname = "Perez", middleName = "Q.", compositionNumber = 17, sensorId = "SENSOR_17"),
                SportsmanSensorUI(id = "18", number = "018", name = "Mia", lastname = "Thompson", middleName = "R.", compositionNumber = 18, sensorId = ""),
                SportsmanSensorUI(id = "19", number = "019", name = "Andrew", lastname = "White", middleName = "S.", compositionNumber = 19, sensorId = ""),
                SportsmanSensorUI(id = "20", number = "020", name = "Ella", lastname = "Lopez", middleName = "T.", compositionNumber = 20, sensorId = ""),
                SportsmanSensorUI(id = "21", number = "021", name = "Ryan", lastname = "Gonzalez", middleName = "U.", compositionNumber = 21, sensorId = "SENSOR_21"),
                SportsmanSensorUI(id = "22", number = "022", name = "Lily", lastname = "Clark", middleName = "V.", compositionNumber = 22, sensorId = ""),
                SportsmanSensorUI(id = "23", number = "023", name = "Brandon", lastname = "Lewis", middleName = "W.", compositionNumber = 23, sensorId = "SENSOR_23"),
                SportsmanSensorUI(id = "24", number = "024", name = "Ava", lastname = "Walker", middleName = "X.", compositionNumber = 24, sensorId = ""),
                SportsmanSensorUI(id = "25", number = "025", name = "Ethan", lastname = "Young", middleName = "Y.", compositionNumber = 25, sensorId = "SENSOR_25")
            ), emptyList(), "", false
        )
    }
}