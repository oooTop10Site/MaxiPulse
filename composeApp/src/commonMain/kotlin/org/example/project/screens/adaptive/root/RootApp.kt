package org.example.project.screens.adaptive.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.mic
import org.example.project.data.model.screen.Screens
import org.example.project.data.model.screen.Screens.*
import org.example.project.data.repository.AiAssistantManager
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.model.AiEvent
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.toTrainingStageChssUI
import org.example.project.platform.SpeechToTextRecognizer
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.screens.adaptive.main.MainScreen
import org.example.project.screens.adaptive.mainTab.MainTabScreen
import org.example.project.screens.adaptive.mainTab.tabs.CompositionsTab
import org.example.project.screens.adaptive.mainTab.tabs.LogTab
import org.example.project.screens.adaptive.mainTab.tabs.MainTab
import org.example.project.screens.adaptive.mainTab.tabs.SensorTab
import org.example.project.screens.adaptive.mainTab.tabs.TestTab
import org.example.project.screens.adaptive.splash.SplashScreen
import org.example.project.screens.tablet.group.GroupScreen
import org.example.project.screens.tablet.log.LogScreen
import org.example.project.screens.tablet.options.utp.UtpScreen
import org.example.project.screens.tablet.sensor.SensorScreen
import org.example.project.screens.tablet.tests.TestsScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiSnackbarHost
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RootApp() {
    val windowSizeClass = calculateWindowSizeClass()
    var isRecording by remember { mutableStateOf(false) }
    var recognizedText by remember { mutableStateOf("") }
    var audioPermission by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    MaxiPulsTheme {
        CompositionLocalProvider(ScreenSize provides windowSizeClass) {
            val stateHost = remember { SnackbarHostState() }
            val viewModel: RootViewModel = viewModel { RootViewModel() }
            LaunchedEffect(viewModel) {
                viewModel.speechRecognizer.setOnResultListener { text ->
                    isRecording = false
                    recognizedText = text
                    println("ОТПРАВКА СООБЩЕНИЕ - $recognizedText")
                    viewModel.sendMessage(recognizedText)
                }
                viewModel.speechRecognizer.setOnPartialResultListener { text ->
                    recognizedText = text
                }
                launch {
                    println("алу я тут")
                    viewModel.observerManager.message.receiveAsFlow().collect {
                        println("errorMessage - $it")
                        if (it.isNotBlank()) {
                            stateHost.showSnackbar(
                                it
                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Navigator(
                    SplashScreen(),
                    disposeBehavior = NavigatorDisposeBehavior(
                    )
                ) { screen ->
                    CompositionLocalProvider(
                        RootNavigator provides screen,
                    ) {
                        TabNavigator(tab = MainTab()) { tabNavigator ->
                            val rootNavigator = RootNavigator.currentOrThrow

                            LaunchedEffect(Unit) {
                                launch {
                                    viewModel.aiManager.eventsScreen.receiveAsFlow().collect {
                                        when (it) {
                                            is AiEvent.ScreenEvent -> {
                                                navigateEvent(
                                                    rootNavigator,
                                                    tabNavigator = tabNavigator,
                                                    it.value
                                                )
                                            }

                                            is AiEvent.TrainingEvent -> {
                                                trainingEvent(rootNavigator, it.value)
                                            }

                                            AiEvent.Unknown -> {
                                                viewModel.observerManager.putMessage("Не удалось распознать сообщение")
                                            }
                                        }
                                    }
                                }
                            }


                            SlideTransition(
                                screen,
                            ) {
                                it.Content()
                            }
//                    val modifierSnackbarHost = when (windowSizeClass.widthSizeClass) {
//                        WindowWidthSizeClass.Compact -> Modifier.fillMaxWidth()
//                            .padding(horizontal = 16.dp)
//
//                        WindowWidthSizeClass.Medium -> Modifier.width(750.dp)
//                        WindowWidthSizeClass.Expanded -> Modifier.width(900.dp)
//                        else -> {
//                            Modifier.fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                        }
//                    }
                            MaxiSnackbarHost(
//                        modifier = modifierSnackbarHost,
                                hostState = stateHost
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    isRecording,
                    modifier = Modifier.size(80.dp).align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier.size(80.dp).border(
                            color = MaxiPulsTheme.colors.uiKit.grey800,
                            shape = CircleShape,
                            width = 1.dp
                        ).align(Alignment.Center).clickableBlank {
                            viewModel.speechRecognizer.stopListening()
                            isRecording = false
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.mic),
                            modifier = Modifier.size(50.dp).align(Alignment.Center),
                            tint = MaxiPulsTheme.colors.uiKit.primary,
                            contentDescription = if (isRecording) "Stop" else "Mic"
                        )
                    }
                }

                AnimatedVisibility(
                    !isRecording,
                    modifier = Modifier.padding(15.dp).size(50.dp).align(Alignment.BottomEnd)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(50.dp).align(Alignment.BottomEnd)
                            .background(
                                MaxiPulsTheme.colors.uiKit.secondary,
                                shape = CircleShape
                            )
                            .clip(CircleShape).clickableBlank(
                                onClick = debouncedClick() {
                                    recognizedText = ""
//                                    if (audioPermission) {
////                                        showRecord = !showRecord
//                                    } else {
                                    scope.launch {
                                        if (viewModel.audioPermissionsService.checkPermission(
                                                Permission.RECORD_AUDIO
                                            )
                                                .granted()
                                        ) {
                                            viewModel.speechRecognizer.startListening()
                                            isRecording = true
                                            audioPermission = true
                                        } else {
                                            viewModel.audioPermissionsService.providePermission(
                                                Permission.RECORD_AUDIO
                                            )
                                        }

                                    }
//                                    }

                                }),
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.mic),
                            modifier = Modifier.size(30.dp),
                            tint = MaxiPulsTheme.colors.uiKit.white,
                            contentDescription = if (isRecording) "Stop" else "Mic"
                        )
                    }
                }

            }
        }
    }
}

fun navigateEvent(navigator: Navigator, tabNavigator: TabNavigator, it: Screens) {
    when (it) {
        UtpScreen -> {
            navigator.push(UtpScreen())
        }

        GroupScreen -> {
            tabNavigator.current = CompositionsTab
            navigator.popUntilRoot()

        }

        SensorsScreen -> {
            tabNavigator.current = SensorTab
            navigator.popUntilRoot()

        }

        HomeScreen -> {
            tabNavigator.current = MainTab()
            navigator.popUntilRoot()

        }

        TestsScreen -> {
            tabNavigator.current = TestTab
            navigator.popUntilRoot()

        }

        MagazineScreen -> {
            tabNavigator.current = LogTab
            navigator.popUntilRoot()

        }
    }
}

fun trainingEvent(navigator: Navigator, trainingStageChssUI: List<TrainingStageChssUI>) {
    navigator.push(MainScreen(stages = trainingStageChssUI))
}
