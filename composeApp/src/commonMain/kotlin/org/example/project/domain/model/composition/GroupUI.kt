package org.example.project.domain.model.composition

data class GroupUI(
    val id: String,
    val title: String,
    val member: Int,
    val avatar: String,
) {
    companion object {
        val Default = GroupUI(
            "", "", 0, ""
        )
    }
}
