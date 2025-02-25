package org.example.project.platformContent.main

import androidx.compose.runtime.Composable
import org.example.project.domain.model.test.TestUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.screens.adaptive.main.MainState
import org.example.project.screens.adaptive.main.MainViewModel
import org.koin.core.component.KoinComponent

@Composable
internal expect fun KoinComponent.MainContent(
    viewModel: MainViewModel,
    state: MainState,
    testUI: TestUI?,
    stages: List<TrainingStageChssUI>,
)