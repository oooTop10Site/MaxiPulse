package org.example.project.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.logo
import maxipuls.composeapp.generated.resources.logo_minipulse
import maxipuls.composeapp.generated.resources.minipulse
import maxipuls.composeapp.generated.resources.yellow_brush
import org.example.project.domain.manager.AuthManager
import org.example.project.screens.login.LoginScreen
import org.example.project.screens.mainTab.MainTabScreen
import org.example.project.screens.mainTab.tabs.MainTab
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.root.ScreenSize
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashScreen : Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val authManager: AuthManager by inject()
        val viewModel = rememberScreenModel { SplashScreenViewModel() }
        val windowSizeClass = ScreenSize.currentOrThrow
        val rootNavigator = RootNavigator.currentOrThrow
        LaunchedEffect(viewModel) {
            launch {
                viewModel.container.sideEffectFlow.collect{
                    when(it) {
                        SplashScreenEvent.Failure -> {rootNavigator.replaceAll(LoginScreen())}
                        SplashScreenEvent.Success -> {
                            delay(500L)
                            rootNavigator.replaceAll(MainTabScreen())
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            when(windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    Image(
                        painter = painterResource(resource = Res.drawable.logo_minipulse),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }

                WindowWidthSizeClass.Medium -> {
                    Image(
                        painter = painterResource(resource = Res.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }

                WindowWidthSizeClass.Expanded -> {
                    Image(
                        painter = painterResource(resource = Res.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }

                else -> {
                    Image(
                        painter = painterResource(resource = Res.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}