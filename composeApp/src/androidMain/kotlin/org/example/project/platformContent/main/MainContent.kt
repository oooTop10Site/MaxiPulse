package org.example.project.platformContent.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.domain.model.test.TestUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.screens.adaptive.main.MainState
import org.example.project.screens.adaptive.main.MainViewModel
import org.example.project.screens.adaptive.main.contents.MainDesktopContent
import org.example.project.screens.adaptive.root.ScreenSize
import org.koin.core.component.KoinComponent

@Composable
internal actual fun KoinComponent.MainContent(
    viewModel: MainViewModel,
    state: MainState,
    testUI: TestUI?,
    stages: List<TrainingStageChssUI>,
) {
    val screenSize = ScreenSize.currentOrThrow
    MainDesktopContent(viewModel, state, testUI, stages)

}