package org.example.project.platform

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.screens.root.RootNavigator

//@Composable
//actual fun BackHandlerPlatform(backHandler: (Navigator, Navigator, () -> Unit) -> Unit) {
//    val context = LocalContext.current
//    val navigator = LocalNavigator.currentOrThrow
//    val rootNavigator = RootNavigator.currentOrThrow
//    BackHandler {
//        backHandler(navigator, rootNavigator) {
//            (context as Activity?)?.moveTaskToBack(true)
//        }
//    }
//}