package org.example.project.ext

import cafe.adriel.voyager.navigator.Navigator
import org.example.project.domain.model.task.MainTaskUI
import org.example.project.screens.borgScale.BorgScaleScreen


fun MainTaskUI.navigate(navigator: Navigator) {
    when(this) {
        is MainTaskUI.BorgScale -> {
            navigator.push(BorgScaleScreen())
        }
        is MainTaskUI.GoodMorning -> {
            //todo
        }
    }
}