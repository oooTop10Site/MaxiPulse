package org.example.project.screens.tablet.miniPulseWidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.widget.MinipulseWidgetTab.*
import org.example.project.screens.tablet.miniPulseWidget.contents.morningRecovery.MorningRecoveryScreen
import org.example.project.screens.tablet.tests.shuttleRun.result.SelectableTab
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.stringResource

class MiniPulseWidgetScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = RootNavigator.currentOrThrow
        val viewModel = rememberScreenModel { MiniPulseWidgetViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.width(315.dp).fillMaxHeight()) {
                    BackIcon(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .size(40.dp)
                    ) { navigator.pop() }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.tabs) {
                            SelectableTab(
                                modifier = Modifier.height(60.dp).fillMaxWidth(),
                                isSelect = it == state.currentTab,
                                text = stringResource(it.title),
                                textStyle = MaxiPulsTheme.typography.bold.copy(
                                    lineHeight = 16.sp,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Start,
                                    color = if (it == state.currentTab) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
                                ),
                            ) {
                                viewModel.changeTab(it)
                            }
                        }
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                when(state.currentTab) {
                    MorningRecovery -> {
                        MorningRecoveryScreen(modifier = Modifier.fillMaxHeight().weight(1f)).Content()
                    }
                    BorgScale -> {}
                    RemoteTraining -> {

                    }
                }
            }
        }
    }
}