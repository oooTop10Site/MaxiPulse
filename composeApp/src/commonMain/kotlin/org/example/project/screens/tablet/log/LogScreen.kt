package org.example.project.screens.tablet.log

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.calendar
import maxipuls.composeapp.generated.resources.composition
import maxipuls.composeapp.generated.resources.event
import maxipuls.composeapp.generated.resources.log
import maxipuls.composeapp.generated.resources.sportsman
import maxipuls.composeapp.generated.resources.trainings_count
import org.example.project.domain.model.log.EventType
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.log.components.LogCard
import org.example.project.screens.tablet.training.trainingResult.TrainingResultScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
class LogScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            LogViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> 1
            WindowWidthSizeClass.Expanded -> 2
            WindowWidthSizeClass.Compact -> 1
            else -> 1
        }
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    TopBarTitle(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    ) { datetime ->
                        Text(
                            text = datetime, style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                            ), maxLines = 1,
                            overflow = TextOverflow.Visible,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                        Text(
                            text = stringResource(
                                Res.string.trainings_count,
                                state.logs.count { it.event.type == EventType.Training }),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Text(
                            text = stringResource(Res.string.log),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(Modifier.size(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        MaxiTextFieldMenu<String>(
                            currentValue = state.filterEvent,
                            text = state.filterEvent,
                            onChangeWorkScope = {
                                viewModel.changeEvent(it)
                            },
                            items = state.filterEvents,
                            itemToString = { it },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.event)
                        )
                        MaxiTextFieldMenu<String>(
                            currentValue = state.filterSportsman,
                            text = state.filterSportsman,
                            onChangeWorkScope = {
                                viewModel.changeSportsman(it)
                            },
                            items = state.filterSportsmans,
                            itemToString = { it },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.sportsman)
                        )
                        MaxiTextFieldMenu<String>(
                            currentValue = state.filterComposition,
                            text = state.filterComposition,
                            onChangeWorkScope = {
                                viewModel.changeComposition(it)
                            },
                            items = state.filterCompositions,
                            itemToString = { it },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.composition)
                        )

                        Icon(
                            painter = painterResource(Res.drawable.calendar),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    }
                    Spacer(Modifier.size(20.dp))


                }
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(chunkSize),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(state.logs) {
                    Box(modifier = Modifier.animateItem()) {
                        LogCard(
                            modifier = Modifier,
                            logUI = it,
                            onDelete = {
                                viewModel.deleteLog(it)
                            }
                        ) {
                            rootNavigator.push(
                                TrainingResultScreen(
                                    emptyList(), emptyList()
                                )
                            )
                        }
                    }
                }
            }
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(20.dp),
//                contentPadding = PaddingValues(20.dp)
//            ) {
//                items(state.logs.chunked(chunkSize)) { chunk ->
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(25.dp)
//                    ) {
//                        chunk.forEach {
//                            LogCard(
//                                modifier = Modifier.weight(1f),
//                                logUI = it,
//                                onDelete = {
//                                    viewModel.deleteLog(it)
//                                }
//                            ) {
//                                rootNavigator.push(TrainingResultScreen())
//                            }
//                        }
//                        if (chunk.size != chunkSize) {
//                            for (i in 1..chunkSize - chunk.size) {
//                                Box(modifier = Modifier.weight(1f).height(100.dp))
//                            }
//                        }
//                    }
//                }
//            }

        }
    }
}