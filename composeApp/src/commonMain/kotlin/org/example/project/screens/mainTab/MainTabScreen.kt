package org.example.project.screens.mainTab

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.logo_small
import maxipuls.composeapp.generated.resources.profile
import org.example.project.ext.clickableBlank
import org.example.project.screens.mainTab.tabs.CompositionsTab
import org.example.project.screens.mainTab.tabs.LoadAnalizeTab
import org.example.project.screens.mainTab.tabs.LogTab
import org.example.project.screens.mainTab.tabs.MainTab
import org.example.project.screens.mainTab.tabs.SensorTab
import org.example.project.screens.mainTab.tabs.SettingsTab
import org.example.project.screens.mainTab.tabs.SportsmanTab
import org.example.project.screens.mainTab.tabs.TestTab
import org.example.project.screens.mainTab.tabs.UTPTab
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerEventType
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.platform.PointerEvent
import org.example.project.platform.pointerEvent
import org.example.project.screens.root.ScreenSize
import org.example.project.theme.uiKit.MaxiSnackbarHost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainTabScreen(private val tab: Tab = MainTab) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val tabsFirst = listOf(MainTab, TestTab, LogTab, UTPTab, LoadAnalizeTab)
        val tabsSecond = listOf(CompositionsTab, SportsmanTab, SensorTab, SettingsTab)
        val navigator = LocalNavigator.currentOrThrow
        var isOpen by remember { mutableStateOf(false) }
        MaxiPageContainer(
            modifier = Modifier.fillMaxSize().background(MaxiPulsTheme.colors.uiKit.background)
        ) {
            TabNavigator(tab) {
                val tabNavigator = LocalTabNavigator.current
                Row(
                    modifier = Modifier
                ) {
                    Column(
                        modifier = Modifier.background(MaxiPulsTheme.colors.uiKit.modalSheet)
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = LinearOutSlowInEasing
                                )
                            ).fillMaxHeight().width(if (isOpen) 270.dp else 110.dp).pointerEvent(
                                PointerEvent.Enter, action = {
                                    isOpen = true
                                }).pointerEvent(PointerEvent.Exit, action = { isOpen = false }),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.size(30.dp))
                        Image(
                            painter = painterResource(Res.drawable.logo_small),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(Modifier.size(30.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

                        Spacer(Modifier.size(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.background(
                                    MaxiPulsTheme.colors.uiKit.background,
                                    shape = CircleShape
                                ).clip(CircleShape).size(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.profile),
                                    modifier = Modifier.size(width = 22.5.dp, 30.dp),
                                    colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                    contentDescription = null
                                )
                            }
                            if (isOpen) {
                                Column(modifier = Modifier.padding(start = 10.dp)) {
                                    Text(
                                        text = "СШОР Гуляев Николай",
                                        style = MaxiPulsTheme.typography.semiBold.copy(
                                            fontSize = 14.sp,
                                            lineHeight = 12.sp,
                                            color = MaxiPulsTheme.colors.uiKit.lightTextColor
                                        )
                                    )

                                    Text(
                                        text = "Вологда",
                                        style = MaxiPulsTheme.typography.semiBold.copy(
                                            fontSize = 14.sp,
                                            color = MaxiPulsTheme.colors.uiKit.lightTextColor
                                        ),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.size(20.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 33.dp).weight(1f),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            contentPadding = PaddingValues(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(tabsFirst + tabsSecond) { tabEach ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth().clickableBlank() {
                                        tabNavigator.current = tabEach
                                    }
                                ) {
                                    tabEach.options.icon?.let {
                                        Icon(
                                            painter = it,
                                            modifier = Modifier.size(30.dp)
                                                .align(Alignment.CenterVertically),
                                            contentDescription = null,
                                            tint = if (tabNavigator.current == tabEach) MaxiPulsTheme.colors.uiKit.primary
                                            else MaxiPulsTheme.colors.uiKit.lightTextColor
                                        )
                                    }
                                    if (isOpen) {
                                        Text(
                                            text = tabEach.options.title,
                                            style = MaxiPulsTheme.typography.medium.copy(
                                                fontSize = 14.sp,
                                                color = if (tabNavigator.current == tabEach) MaxiPulsTheme.colors.uiKit.primary
                                                else MaxiPulsTheme.colors.uiKit.lightTextColor
                                            ),
                                            modifier = Modifier.padding(start = 18.dp).weight(1f)
                                        )
                                    }
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

                        Spacer(Modifier.size(20.dp))

                        Box(
                            modifier = Modifier.padding(horizontal = 20.dp).height(44.dp)
                                .width(if (isOpen) 190.dp else 65.dp).clip(
                                    RoundedCornerShape(50.dp)
                                ).background(MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.5f))
                                .clickable {
                                    isOpen = !isOpen
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.back_ic),
                                modifier = Modifier.size(24.dp)
                                    .rotate(if (isOpen) 0f else 180f),
                                contentDescription = null,
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }

                        Spacer(Modifier.size(20.dp))

                    }
                    it.current.Content()
                }

            }
        }
    }
}