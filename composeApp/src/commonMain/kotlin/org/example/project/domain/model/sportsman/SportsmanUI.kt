package org.example.project.domain.model.sportsman

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
    val isMale: Boolean
) {
    val fio: String
        get() = "$lastname $name $middleName"

    companion object {
        val Default = SportsmanUI(
            "", 0, "", "", "", 0, 0, 0, "", true
        )
    }
}
