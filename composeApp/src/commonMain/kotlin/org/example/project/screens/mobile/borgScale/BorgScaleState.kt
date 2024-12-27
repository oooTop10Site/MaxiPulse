package org.example.project.screens.mobile.borgScale

import org.example.project.domain.model.scaleBorg.ScaleBorg

data class BorgScaleState(
    val selectItem: ScaleBorg?,
    val items: List<ScaleBorg>
) {
    companion object {
        val InitState = BorgScaleState(
            null, items = listOf(
                ScaleBorg.Scale1,
                ScaleBorg.Scale2,
                ScaleBorg.Scale3,
                ScaleBorg.Scale4,
                ScaleBorg.Scale5,
                ScaleBorg.Scale6,
                ScaleBorg.Scale7,
                ScaleBorg.Scale8,
                ScaleBorg.Scale9,
            )
        )
    }
}