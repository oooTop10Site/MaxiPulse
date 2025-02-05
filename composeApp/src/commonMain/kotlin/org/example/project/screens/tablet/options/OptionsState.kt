package org.example.project.screens.tablet.options

import kotlinx.coroutines.selects.select
import org.example.project.domain.model.option.Option

data class OptionsState(
    val items: List<Option>,
    val selectOption: Option?
) {
    companion object {
        val InitState = OptionsState(
            items = Option.entries,
            selectOption = null
        )
    }
}