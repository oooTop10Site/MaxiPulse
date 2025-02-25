package org.example.project.theme.uiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import kotlinx.coroutines.launch
import org.example.project.theme.MaxiPulsTheme
import org.example.project.utils.currentTimeFlow
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.log
import org.example.project.ext.toUI
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@OptIn(InternalVoyagerApi::class)
@Composable
fun MaxiPageContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topDivider: Boolean = true,
    floatingActionButton: (@Composable () -> Unit)? = null,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val localFocusManager = LocalFocusManager.current
//    val navigator = LocalNavigator.currentOrThrow
//    val bottomBarVisibleManager: BottomBarVisibleManager by inject()
//    LaunchedEffect(Unit) {
//        bottomBarVisibleManager.setBottomBarVisibility(navigator.key)
//    }
    Box(modifier = modifier.fillMaxSize().pointerInput(Unit) {
        detectTapGestures(onTap = {
            localFocusManager.clearFocus()
        })
    }.background(MaxiPulsTheme.colors.uiKit.background)) {
        Column(
            modifier = Modifier
        ) {
            Column {
                topBar?.invoke()
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                content()
                floatingActionButton?.let {
                    Box(
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 20.dp, bottom = 22.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        it()
                    }
                }
            }
            bottomBar?.invoke()
        }
    }
}

@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier,
    text: String,
    showCurrentTime: Boolean,
    traingleIcon: (@Composable () -> Unit)? = null,
) {
    var datetime: String by remember { mutableStateOf("...") }
    LaunchedEffect(Unit) {
        if (showCurrentTime) {
            launch {
                currentTimeFlow().collect {
                    datetime = "${it.date.toUI()}, ${it.time.toUI()}"
                }
            }
        }
    }
    Box(modifier) {
        if (showCurrentTime) {
            Text(
                text = datetime, style = MaxiPulsTheme.typography.regular.copy(
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                ), maxLines = 1,
                overflow = TextOverflow.Visible,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Text(
            text = text,
            style = MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        Box(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 20.dp)) {
            traingleIcon?.invoke()
        }
    }
}

@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier,
    content: (@Composable BoxScope.(String) -> Unit),
) {
    var datetime: String by remember { mutableStateOf("...") }
    LaunchedEffect(Unit) {
        launch {
            currentTimeFlow().collect {
                datetime = "${it.date.toUI()}, ${it.time.toUI()}"
            }
        }
    }
    Box(modifier) {
        content.invoke(this, datetime)
    }
}

//mobile

@OptIn(InternalVoyagerApi::class)
@Composable
fun MaxiPageContainerMobile(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val localFocusManager = LocalFocusManager.current
//    val navigator = LocalNavigator.currentOrThrow
//    val bottomBarVisibleManager: BottomBarVisibleManager by inject()
//    LaunchedEffect(Unit) {
//        bottomBarVisibleManager.setBottomBarVisibility(navigator.key)
//    }
    Column {
        Scaffold(
            containerColor = MaxiPulsTheme.colors.uiKit.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f).pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
            topBar = {
                Box(
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                        .background(MaxiPulsTheme.colors.uiKit.background),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxHeight(),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors(containerColor = MaxiPulsTheme.colors.uiKit.background)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            topBar?.let { top ->
                                top()
                            }
                        }
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(MaxiPulsTheme.colors.uiKit.background)
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        )
                ) {
                    content()
                }
            }
        )
    }
}

@Composable
fun TopBarMobile(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    title: String,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier.align(Alignment.CenterStart),
            contentAlignment = Alignment.Center
        ) {
            leadingIcon?.invoke()
        }

        Text(
            text = title, style = MaxiPulsTheme.typography.medium.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}