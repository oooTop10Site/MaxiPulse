package org.example.project.screens.tablet.sportsman.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.chss_pano
import maxipuls.composeapp.generated.resources.chss_pao
import maxipuls.composeapp.generated.resources.chss_resting
import maxipuls.composeapp.generated.resources.female_ic
import maxipuls.composeapp.generated.resources.height
import maxipuls.composeapp.generated.resources.imt
import maxipuls.composeapp.generated.resources.marker_question
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.number_player
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.save
import maxipuls.composeapp.generated.resources.sensor
import maxipuls.composeapp.generated.resources.sensor_ic
import maxipuls.composeapp.generated.resources.sensor_not_choose
import maxipuls.composeapp.generated.resources.sportsman_ic
import maxipuls.composeapp.generated.resources.turn_on_personal_sensor
import maxipuls.composeapp.generated.resources.weight
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.tablet.sportsman.edit.SportsmanEditScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.HeartBPMGraph
import org.example.project.theme.uiKit.HeartRateGraph
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.example.project.utils.toStringWithCondition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class SportsmanDetailScreen(private val gamerId: String? = null) : Screen {

    @Composable
    override fun Content() {
        MaxiPageContainer() {
            val viewModel = rememberScreenModel {
                SportsmanDetailViewModel()
            }
            val rootNavigator = RootNavigator.currentOrThrow
            val state by viewModel.stateFlow.collectAsState()

            LaunchedEffect(viewModel) {
                viewModel.loadSportsman(id = gamerId)

                launch {
                    viewModel.container.sideEffectFlow.collect {
                        when (it) {
                            SportsmanDetailEvent.Save -> {
                                rootNavigator.pop()
                            }
                        }
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp)
                                .background(MaxiPulsTheme.colors.uiKit.primary, shape = CircleShape)
                                .clip(CircleShape).clickableBlank(role = Role.Button) {
                                    rootNavigator.pop()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.back_ic),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }

                        Text(
                            text = stringResource(Res.string.profile),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Box(
                            modifier = Modifier.size(40.dp)
                                .background(
                                    MaxiPulsTheme.colors.uiKit.primary,
                                    shape = CircleShape
                                )
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.pencil),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                                    .clickableBlank(onClick = debouncedClick() {
                                        rootNavigator.push(SportsmanEditScreen(gamerId = gamerId))
                                    }),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(200.dp),
                        ) {
                            if (state.sportsmanUI.avatar.isBlank()) {
                                Box(
                                    modifier = Modifier.fillMaxSize().background(
                                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                        shape = CircleShape
                                    ).clip(CircleShape).align(Alignment.Center)
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.profile),
                                        modifier = Modifier.size(
                                            width = 90.dp,
                                            120.dp
                                        ).align(Alignment.Center),
                                        colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                        contentDescription = null
                                    )
                                }
                            } else {
                                MaxiImage(
                                    modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                        .clip(CircleShape),
                                    url = state.sportsmanUI.avatar,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Box(
                                modifier = Modifier.background(
                                    MaxiPulsTheme.colors.uiKit.grey800,
                                    shape = CircleShape
                                ).clip(CircleShape).size(65.dp).align(Alignment.BottomEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.sportsmanUI.number.toString(),
                                    style = MaxiPulsTheme.typography.semiBold.copy(
                                        color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                        fontSize = 24.sp,
                                        lineHeight = 24.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(Modifier.size(30.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = state.sportsmanUI.lastname,
                                style = MaxiPulsTheme.typography.semiBold.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 20.sp,
                                    lineHeight = 20.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.size(4.dp))
                            Text(
                                text = "${state.sportsmanUI.name} ${state.sportsmanUI.middleName}",
                                style = MaxiPulsTheme.typography.semiBold.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 20.sp,
                                    lineHeight = 20.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(Modifier.size(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(
                                        Res.string.age_text,
                                        state.sportsmanUI.age
                                    ),
                                    style = MaxiPulsTheme.typography.regular.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 14.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, false)
                                )

                                Spacer(Modifier.size(10.dp))

                                Icon(
                                    if (state.sportsmanUI.isMale) painterResource(Res.drawable.sportsman_ic) else painterResource(
                                        Res.drawable.female_ic
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                            Spacer(Modifier.size(15.dp))

                            Text(
                                text = "Этап подготовки: УТЭ2",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, false)
                            )

                        }
                        val colors = ButtonDefaults.buttonColors()
                        MaxiButton(
                            buttonTextStyle = ButtonTextStyle.Medium,
                            text = if (state.sensorUI == null) stringResource(Res.string.sensor_not_choose) else "${state.sensorUI?.sensorId.orEmpty()}\n${state.sensorUI?.deviceName.orEmpty()}",
                            modifier = Modifier.height(65.dp).width(250.dp),
                            colors = if (state.sensorUI == null) ButtonDefaults.buttonColors(
                                containerColor = colors.disabledContainerColor,
                                contentColor = colors.disabledContentColor
                            ) else ButtonDefaults.buttonColors(
                                containerColor = MaxiPulsTheme.colors.uiKit.buttonContainer,
                                contentColor = MaxiPulsTheme.colors.uiKit.buttonContent
                            ),
                            shape = RoundedCornerShape(50.dp),
                            onClick = {
                                rootNavigator.push(SportsmanEditScreen(gamerId))
                            })
                    }

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.number.toString(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.number_player),
                            )

                            Spacer(Modifier.size(52.dp))

                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.mpk.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.mpk),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.height.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.height),
                            )

                            Spacer(Modifier.size(52.dp))

                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.chssPano.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.chss_pano),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.weight.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.weight),
                            )

                            Spacer(Modifier.size(52.dp))

                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.chssPao.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.chss_pao),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.chssMax.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.chss_max),
                            )

                            Spacer(Modifier.size(52.dp))

                            MaxiOutlinedTextField(
                                value = state.imt,
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.imt),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            MaxiOutlinedTextField(
                                value = state.sportsmanUI.chssResting.toStringWithCondition(),
                                onValueChange = {
                                },
                                readOnly = true,
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                                placeholder = stringResource(Res.string.chss_resting),
                            )

                            Spacer(Modifier.size(52.dp))

                            Box(
                                modifier = Modifier.weight(1f).height(Constants.TextFieldHeight),
                            )
                        }
                    }
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Column(modifier = Modifier) {
                    Spacer(Modifier.size(20.dp))
                    Icon(
                        painterResource(Res.drawable.marker_question),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 20.dp).size(24.dp).clickableBlank {
                            viewModel.changeDialog()
                        }.align(Alignment.End),
                        tint = MaxiPulsTheme.colors.uiKit.grey800
                    )
                    Spacer(Modifier.size(11.dp))

                    Row(modifier = Modifier.weight(1f)) {
                        Spacer(Modifier.size(20.dp))
                        HeartBPMGraph(modifier = Modifier.width(150.dp))
                        Spacer(Modifier.size(20.dp))
                    }
                    Spacer(Modifier.size(20.dp))

                }
            }
            if (state.isOpenDialog) {
                MaxiAlertDialog(
                    modifier = Modifier.fillMaxWidth(0.8f).padding(50.dp),
                    onDismiss = { viewModel.changeDialog() },
                    accept = { viewModel.changeDialog() },
                    cancel = {
                        viewModel.changeDialog()
                    },
                    title = stringResource(Res.string.chss),
                    acceptText = stringResource(Res.string.save),
                    cancelText = stringResource(Res.string.cancel),
                    alertDialogButtons = null,
                    descriptionContent = {
                        val heartRateData = listOf(
                            HeartBit(mills = 0L, value = 60),
                            HeartBit(mills = 1000L, value = 70),
                            HeartBit(mills = 2000L, value = 80),
                            HeartBit(mills = 3000L, value = 150),
                            HeartBit(mills = 4000L, value = 170),
                            HeartBit(mills = 5000L, value = 180),
                            HeartBit(mills = 6000L, value = 200),
                            HeartBit(mills = 7000L, value = 215),
                            HeartBit(mills = 8000L, value = 217)
                        ) //mock
                        HeartRateGraph(
                            modifier = Modifier.height(250.dp).fillMaxWidth(),
                            heartRateData
                        )
                    },
                )
            }
        }
    }
}