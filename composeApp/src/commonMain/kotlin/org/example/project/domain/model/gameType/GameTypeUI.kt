package org.example.project.domain.model.gameType

data class GameTypeUI(
    val id: String,
    val name: String
) {
    companion object {
        val Default = GameTypeUI(
            "", ""
        )
    }
}