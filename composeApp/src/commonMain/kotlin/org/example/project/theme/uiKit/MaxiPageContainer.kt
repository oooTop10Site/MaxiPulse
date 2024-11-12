package org.example.project.theme.uiKit

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi

@OptIn(InternalVoyagerApi::class)
@Composable
fun MaxiPageContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val localFocusManager = LocalFocusManager.current
//    val navigator = LocalNavigator.currentOrThrow
//    val bottomBarVisibleManager: BottomBarVisibleManager by inject()
//    LaunchedEffect(Unit) {
//        bottomBarVisibleManager.setBottomBarVisibility(navigator.key)
//    }
    Column(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                localFocusManager.clearFocus()
            })
        }
    ) {
        topBar?.invoke()
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        bottomBar?.invoke()
    }
}