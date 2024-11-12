package org.example.project.screens.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.logo
import org.example.project.domain.manager.AuthManager
import org.example.project.screens.loginScreen.LoginScreen
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.root.ScreenSize
import org.jetbrains.compose.resources.imageResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashScreen : Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val authManager: AuthManager by inject()
        val windowSizeClass = ScreenSize.currentOrThrow
        val rootNavigator = RootNavigator.currentOrThrow
        LaunchedEffect(Unit) {
            delay(500L)
//            if(authManager.token.isNullOrBlank()) {
                rootNavigator.push(LoginScreen())
//            } else {
//                //nav to mainTab
//            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 50.dp), contentAlignment = Alignment.Center) {
            Image(
                bitmap = imageResource(resource = Res.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}