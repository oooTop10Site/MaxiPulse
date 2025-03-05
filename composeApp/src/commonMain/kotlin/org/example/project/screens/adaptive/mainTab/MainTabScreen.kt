package org.example.project.screens.adaptive.mainTab

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.logo_small
import maxipuls.composeapp.generated.resources.profile
import org.example.project.domain.model.AiEvent
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.painterResource
import org.example.project.platform.PointerEvent
import org.example.project.platform.pointerEvent
import org.example.project.screens.adaptive.mainTab.tabs.CompositionsTab
import org.example.project.screens.adaptive.mainTab.tabs.LogTab
import org.example.project.screens.adaptive.mainTab.tabs.MainTab
import org.example.project.screens.adaptive.mainTab.tabs.SensorTab
import org.example.project.screens.adaptive.mainTab.tabs.SettingsTab
import org.example.project.screens.adaptive.mainTab.tabs.SportsmanTab
import org.example.project.screens.adaptive.mainTab.tabs.TestTab
import org.example.project.screens.adaptive.mainTab.tabs.OptionTab
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.RootViewModel
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.adaptive.root.navigateEvent
import org.example.project.screens.adaptive.root.trainingEvent
import org.koin.core.component.KoinComponent

class MainTabScreen() : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val screenSize = ScreenSize.currentOrThrow
        val navigator = LocalNavigator.currentOrThrow
        var isOpen = remember { mutableStateOf(false) }
        val viewModel: RootViewModel = viewModel { RootViewModel() }
        val tabNavigator = LocalTabNavigator.current

        MaxiPageContainer(
            modifier = Modifier.fillMaxSize().background(MaxiPulsTheme.colors.uiKit.background)
        ) {

                val rootNavigator =  RootNavigator.currentOrThrow
                when (screenSize.widthSizeClass) {
                    WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                        LargeLeftMenu(isOpen, tabNavigator, tabNavigator)
                    }

                    WindowWidthSizeClass.Expanded -> {
                        LargeLeftMenu(isOpen, tabNavigator, tabNavigator)
                    }

                    else -> {
                        LargeLeftMenu(isOpen, tabNavigator, tabNavigator)
                    }
                }
        }

    }

}

@Composable
private fun LargeLeftMenu(
    isOpen: MutableState<Boolean>,
    tabNavigator: TabNavigator,
    navigator1: TabNavigator
) {
    val tabsFirst = listOf(MainTab(), TestTab, LogTab, OptionTab)
    val tabsSecond = listOf(CompositionsTab, SportsmanTab, SensorTab, SettingsTab)
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
                ).fillMaxHeight().width(if (isOpen.value) 270.dp else 110.dp).pointerEvent(
                    PointerEvent.Enter, action = {
                        isOpen.value = true
                    }).pointerEvent(PointerEvent.Exit, action = { isOpen.value = false }),
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
                if (isOpen.value) {
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
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
                    .padding(horizontal = 33.dp).weight(1f),
//                contentPadding = PaddingValues(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    tabsFirst.forEach { tabEach ->
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
                                    tint = if (tabNavigator.current.key == tabEach.key) MaxiPulsTheme.colors.uiKit.primary
                                    else MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }
                            if (isOpen.value) {

                                Text(
                                    text = tabEach.options.title,
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        fontSize = 14.sp,
                                        color = if (tabNavigator.current.key == tabEach.key) MaxiPulsTheme.colors.uiKit.primary
                                        else MaxiPulsTheme.colors.uiKit.lightTextColor
                                    ),
                                    modifier = Modifier.padding(start = 18.dp).weight(1f)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.size(60.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    tabsSecond.forEach { tabEach ->
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
                                    tint = if (tabNavigator.current.key == tabEach.key) MaxiPulsTheme.colors.uiKit.primary
                                    else MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }
                            if (isOpen.value) {

                                Text(
                                    text = tabEach.options.title,
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        fontSize = 14.sp,
                                        color = if (tabNavigator.current.key == tabEach.key) MaxiPulsTheme.colors.uiKit.primary
                                        else MaxiPulsTheme.colors.uiKit.lightTextColor
                                    ),
                                    modifier = Modifier.padding(start = 18.dp).weight(1f)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.size(20.dp))

            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(Modifier.size(20.dp))

            Box(
                modifier = Modifier.padding(horizontal = 20.dp).height(44.dp)
                    .width(if (isOpen.value) 190.dp else 65.dp).clip(
                        RoundedCornerShape(50.dp)
                    ).background(MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.5f))
                    .clickable {
                        isOpen.value = !isOpen.value
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.back_ic),
                    modifier = Modifier.size(24.dp)
                        .rotate(if (isOpen.value) 0f else 180f),
                    contentDescription = null,
                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                )
            }

            Spacer(Modifier.size(20.dp))

        }
        navigator1.current.Content()
    }
}