package org.example.project.screens.adaptive.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.ok
import org.example.project.domain.model.test.TestUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.ext.granted
import org.example.project.platform.SpeechToTextRecognizer
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.platformContent.main.MainContent
import org.example.project.screens.adaptive.main.contents.MainDesktopContent
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class MainScreen(val testUI: TestUI? = null,   val stages: List<TrainingStageChssUI> = emptyList<TrainingStageChssUI>()) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MainViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()

        MainContent(viewModel, state, testUI, stages)
    }
}