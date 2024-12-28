package org.example.project.ext

import cafe.adriel.voyager.navigator.Navigator
import org.example.project.domain.model.task.MainTaskUI
import org.example.project.screens.mobile.borgScale.BorgScaleScreen
import org.example.project.screens.mobile.training.MobileTrainingScreen


fun MainTaskUI.navigate(navigator: Navigator) {
    when (this) {
        is MainTaskUI.BorgScale -> {
            navigator.push(BorgScaleScreen())
        }

        is MainTaskUI.GoodMorning -> {
            //todo
        }

        is MainTaskUI.Training -> {
            navigator.push(MobileTrainingScreen())
        }
    }
}