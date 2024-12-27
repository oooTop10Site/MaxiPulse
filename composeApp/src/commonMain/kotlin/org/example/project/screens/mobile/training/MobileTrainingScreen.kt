package org.example.project.screens.mobile.training

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue

class MobileTrainingScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MobileTrainingViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
    }
}