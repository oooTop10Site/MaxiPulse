package org.example.project.screens.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.connect
import maxipuls.composeapp.generated.resources.connecting
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.on_active_sensors
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.sportsman
import maxipuls.composeapp.generated.resources.start_tarining
import org.example.project.screens.main.components.SportsmanSensorCard
import org.example.project.screens.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiSwitch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class MainScreen : Screen {
    @Composable
    override fun Content() {
        MaxiPageContainer() {
            val viewModel = rememberScreenModel {
                MainViewModel()
            }
            val state by viewModel.stateFlow.collectAsState()
            val screenSize = ScreenSize.currentOrThrow
            val chunkSize = when (screenSize.widthSizeClass) {
                WindowWidthSizeClass.Medium -> 2
                WindowWidthSizeClass.Expanded -> 3
                WindowWidthSizeClass.Compact -> 1
                else -> 1
            }
            val buttonDivision = when (screenSize.heightSizeClass) {
                WindowHeightSizeClass.Medium -> 1.4
                WindowHeightSizeClass.Expanded -> 1.0
                WindowHeightSizeClass.Compact -> 1.7
                else -> 1.5
            }
            MaxiPageContainer(
                bottomBar = {
                    MaxiButton(
                        onClick = {}, shape = RoundedCornerShape(0.dp), text = stringResource(
                            Res.string.start_tarining
                        ), modifier = Modifier.fillMaxWidth().height((94 / buttonDivision).dp)
                    )
                },
                topBar = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.size(20.dp))
                        Text(
                            text = stringResource(Res.string.connecting),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        )
                        Text(
                            text = stringResource(
                                Res.string.connect,
                                state.sportsmans.count { it.sensorId.isNotEmpty() }),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 4.dp)
                        )
                        Spacer(
                            Modifier.size(12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.on_active_sensors),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            MaxiSwitch(
                                checked = state.isActiveSensor,
                                onCheckedChange = {
                                    viewModel.changeIsActiveSensor()
                                }
                            )
                            MaxiOutlinedTextField(
                                value = state.search,
                                onValueChange = {
                                    viewModel.changeSearch(it)
                                },
                                placeholder = stringResource(Res.string.search),
                                modifier = Modifier.height(40.dp).weight(1f),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(Res.drawable.search),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaxiPulsTheme.colors.uiKit.textColor
                                    )
                                }
                            )
                        }

                        Spacer(
                            Modifier.size(20.dp)
                        )


                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(state.sportsmans.chunked(chunkSize)) { chunk ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            chunk.forEach {
                                val isSelect = it.id in state.selectSportsmans
                                SportsmanSensorCard(
                                    modifier = Modifier.weight(1f),
                                    name = it.name,
                                    number = it.number,
                                    middleName = it.middleName,
                                    lastname = it.lastname,
                                    compositionText = "Состав - ${it.compositionNumber}",
                                    sensorId = it.sensorId,
                                    isSelect = isSelect,
                                    isBorder = state.isActiveSensor && isSelect
                                ) {
                                    viewModel.changeSelect(it)
                                }
                            }
                            if (chunk.size != chunkSize) {
                                for (i in 1..chunkSize - chunk.size) {
                                    Box(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

            }
        }
    }

}