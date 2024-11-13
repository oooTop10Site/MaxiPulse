package org.example.project.domain.model.composition

import com.seiko.imageloader.component.mapper.StringUriMapper

data class CompositionUI(
    val id: String,
    val title: String,
    val member: Int,
) {
    companion object {
        val Default = CompositionUI(
            "", "", 0
        )
    }
}
