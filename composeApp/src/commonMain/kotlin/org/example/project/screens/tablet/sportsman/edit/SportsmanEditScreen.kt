package org.example.project.screens.tablet.sportsman.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_large_ic
import maxipuls.composeapp.generated.resources.attention
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.biometrics_indicators
import maxipuls.composeapp.generated.resources.by_age
import maxipuls.composeapp.generated.resources.by_zone_chss
import maxipuls.composeapp.generated.resources.calendar
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.canceled
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.chss_pano
import maxipuls.composeapp.generated.resources.chss_pao
import maxipuls.composeapp.generated.resources.composition_and_group
import maxipuls.composeapp.generated.resources.couch
import maxipuls.composeapp.generated.resources.counting
import maxipuls.composeapp.generated.resources.date_birthday
import maxipuls.composeapp.generated.resources.delete_sportsman_alert
import maxipuls.composeapp.generated.resources.height
import maxipuls.composeapp.generated.resources.imt
import maxipuls.composeapp.generated.resources.lastname
import maxipuls.composeapp.generated.resources.middlename
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.name_or_nick
import maxipuls.composeapp.generated.resources.number_player
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.right_ic
import maxipuls.composeapp.generated.resources.save
import maxipuls.composeapp.generated.resources.settings_chss_zone
import maxipuls.composeapp.generated.resources.sex
import maxipuls.composeapp.generated.resources.sport
import maxipuls.composeapp.generated.resources.sport_category
import maxipuls.composeapp.generated.resources.sport_training_indicators
import maxipuls.composeapp.generated.resources.stage_sport_ready
import maxipuls.composeapp.generated.resources.trash
import maxipuls.composeapp.generated.resources.turn_on_personal_sensor
import maxipuls.composeapp.generated.resources.weight
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.MainAlertDialog.SelectSensor
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.rank.RankUI
import org.example.project.domain.model.trainingStage.TrainingStageUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.granted
import org.example.project.ext.isMaleToString
import org.example.project.ext.toUI
import org.example.project.platform.ImagePicker
import org.example.project.platform.permission.model.Permission
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.HeartBPMGraph
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.theme.uiKit.MaxiTextFieldResMenu
import org.example.project.theme.uiKit.SelectSensor
import org.example.project.theme.uiKit.ThresholdEditor
import org.example.project.theme.uiKit.simpleVerticalScrollbar
import org.example.project.utils.Constants
import org.example.project.utils.previousOrPresentSelectableDates
import org.example.project.utils.toStringWithCondition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent

class SportsmanEditScreen(private val gamerId: String? = null) : Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var imagePickerShow by remember { mutableStateOf(false) }
        var imagePickerPermission by remember { mutableStateOf(false) }
        val viewModel = rememberScreenModel {
            SportsmanEditViewModel()
        }
        val openDialog = remember { mutableStateOf(false) }
        val instant by remember { mutableStateOf(Clock.System.now()) }
        val datePickerState =
            rememberDatePickerState(selectableDates = previousOrPresentSelectableDates(instant))

        MaxiPageContainer() {
            val rootNavigator = RootNavigator.currentOrThrow
            val state by viewModel.stateFlow.collectAsState()
            val scope = rememberCoroutineScope()
            viewModel.imagePermissionsService.checkPermissionFlow(Permission.READ_EXTERNAL_STORAGE)
                .collectAsState(viewModel.imagePermissionsService.checkPermission(Permission.READ_EXTERNAL_STORAGE))
                .granted {
                    if (imagePickerPermission) {
                        imagePickerShow = true
                    }
                }

            LaunchedEffect(viewModel) {
                viewModel.loadSportsman(id = gamerId)
                viewModel.loadGameTypes()
                viewModel.loadGroups()
                launch {
                    viewModel.container.sideEffectFlow.collect {
                        when (it) {
                            SportsmanEditEvent.Save -> {
                                rootNavigator.pop()
                            }

                            SportsmanEditEvent.Delete -> {
                                rootNavigator.popUntilRoot()
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
                        if(gamerId != null) {
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
                                    painterResource(Res.drawable.trash),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp).clickableBlank {
                                        viewModel.deleteSportsmanDialog()
                                    },
                                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }
                        }
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(200.dp).clickableBlank {
                                scope.launch {
                                    if (viewModel.imagePermissionsService.checkPermission(Permission.READ_EXTERNAL_STORAGE)
                                            .granted()
                                    ) {
                                        imagePickerShow = true
                                    } else {
                                        imagePickerPermission = true
                                        viewModel.imagePermissionsService.providePermission(
                                            Permission.READ_EXTERNAL_STORAGE
                                        )
                                    }

                                }
                            },
                        ) {
                            if (state.sportsmanUI.avatar.isBlank()) {
                                Box(
                                    modifier = Modifier.fillMaxSize().background(
                                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                        shape = CircleShape
                                    ).clip(CircleShape).align(Alignment.Center)
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.add_large_ic),
                                        modifier = Modifier.size(
                                            100.dp
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
                        Spacer(Modifier.size(20.dp))
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.weight(1f).heightIn(max = 200.dp)
                        ) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.lastname,
                                    onValueChange = {
                                        viewModel.changeLastname(it)

                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .width(200.dp),
                                    placeholder = stringResource(Res.string.lastname),
                                )
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.name,
                                    onValueChange = {
                                        viewModel.changeFirstname(it)
                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .width(200.dp),
                                    placeholder = stringResource(Res.string.name_or_nick),
                                )
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.middleName,
                                    onValueChange = {
                                        viewModel.changeMiddleName(it)
                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .width(200.dp),
                                    placeholder = stringResource(Res.string.middlename),
                                )
                            }

                            Spacer(Modifier.size(20.dp))

                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    MaxiOutlinedTextField(
                                        value = state.sportsmanUI.birthDay?.toUI().orEmpty(),
                                        onValueChange = {
                                        },
                                        readOnly = true,
                                        onClick = {
                                            openDialog.value = true
                                        },
                                        trailingIcon = {
                                            Icon(
                                                painterResource(Res.drawable.calendar),
                                                modifier = Modifier.size(24.dp).clickableBlank {
                                                    openDialog.value = true
                                                },
                                                contentDescription = null
                                            )
                                        },
                                        modifier = Modifier.height(Constants.TextFieldHeight)
                                            .weight(0.8f),
                                        placeholder = stringResource(Res.string.date_birthday),
                                    )
                                    Spacer(Modifier.size(20.dp))
                                    val currentTrainingStage =
                                        state.trainingStages.find { it.id == state.sportsmanUI.trainigStageId }
                                    MaxiTextFieldMenu<TrainingStageUI>(
                                        currentValue = currentTrainingStage
                                            ?: TrainingStageUI.Default,
                                        text = currentTrainingStage?.name.orEmpty(),
                                        onChangeWorkScope = {
                                            viewModel.changeTrainingStage(it)
                                        },
                                        modifier = Modifier.height(Constants.TextFieldHeight)
                                            .weight(1f),
                                        placeholderText = stringResource(Res.string.stage_sport_ready),
                                        items = state.trainingStages,
                                        itemToString = {
                                            it.name
                                        },
//                                        enabled = state.sportsmanUI.gameTypeId.isNotBlank()
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    MaxiTextFieldResMenu<Boolean>(
                                        currentValue = state.sportsmanUI.isMale,
                                        text = stringResource(state.sportsmanUI.isMale.isMaleToString()),
                                        onChangeWorkScope = {
                                            viewModel.changeSex(it)
                                        },
                                        modifier = Modifier.height(Constants.TextFieldHeight)
                                            .weight(0.8f),
                                        placeholderText = stringResource(Res.string.sex),
                                        items = listOf(true, false),
                                        itemToString = {
                                            it.isMaleToString()
                                        },
                                        trailingIcon = {
                                            Icon(
                                                painter = painterResource(Res.drawable.sex),
                                                contentDescription = null,
                                                modifier = Modifier.padding(end = 5.dp)
                                                    .widthIn(max = 56.dp)
                                                    .heightIn(max = Constants.TextFieldHeight)
                                            )
                                        },
                                    )
                                    Spacer(Modifier.size(20.dp))
                                    val currentRankUI =
                                        state.ranks.find { it.id == state.sportsmanUI.rankId }
                                    MaxiTextFieldMenu<RankUI>(
                                        currentValue = currentRankUI ?: RankUI.Default,
                                        text = currentRankUI?.name.orEmpty(),
                                        onChangeWorkScope = {
                                            viewModel.changeRank(it)
                                        },
                                        modifier = Modifier.height(Constants.TextFieldHeight)
                                            .weight(1f),
                                        placeholderText = stringResource(Res.string.sport_category),
                                        items = state.ranks,
                                        itemToString = {
                                            it.name
                                        },
//                                        enabled = state.sportsmanUI.gameTypeId.isNotBlank()
                                    )
                                }
                                MaxiButton(
                                    onClick = {
                                        viewModel.changeSensorAlertDialog()
                                    },
                                    buttonTextStyle = ButtonTextStyle.Medium,
                                    text = if (state.sensorUI == null) stringResource(Res.string.turn_on_personal_sensor) else "${state.sensorUI?.sensorId.orEmpty()} ${state.sensorUI?.deviceName.orEmpty()}",
                                    modifier = Modifier.height(65.dp).widthIn(max = 320.dp),
                                    shape = RoundedCornerShape(15.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    val listState = rememberLazyListState()
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth().weight(1f)
                            .simpleVerticalScrollbar(state = listState, width = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(top = 20.dp, bottom = 52.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(80.dp)
                            ) {
                                val currentGameType =
                                    state.gameTypes.find { it.id == state.sportsmanUI.gameTypeId }
                                MaxiTextFieldMenu<GameTypeUI>(
                                    text = currentGameType?.name.orEmpty(),
                                    currentValue = currentGameType ?: GameTypeUI.Default,
                                    onChangeWorkScope = {
                                        viewModel.changeSport(it)
                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .weight(1f),
                                    placeholderText = stringResource(Res.string.sport),
                                    items = state.gameTypes,
                                    itemToString = {
                                        it.name
                                    },
                                )
                                val currentGroup =
                                    state.groups.find { it.id == state.sportsmanUI.groupId }
                                MaxiTextFieldMenu<GroupUI>(
                                    text = currentGroup?.title.orEmpty(),
                                    currentValue = currentGroup ?: GroupUI.Default,
                                    onChangeWorkScope = {
                                        viewModel.changeGroup(it)
                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .weight(1f),
                                    placeholderText = stringResource(Res.string.composition_and_group),
                                    items = state.groups,
                                    itemToString = {
                                        it.title
                                    },

                                    )

                                MaxiTextFieldResMenu<String>(
                                    text = "",
                                    currentValue = "",
                                    onChangeWorkScope = {

                                    },
                                    modifier = Modifier.height(Constants.TextFieldHeight)
                                        .weight(1f),
                                    placeholderText = stringResource(Res.string.couch),
                                    items = listOf(),
                                    itemToString = {
                                        Res.string.ok
                                    },
                                )
                            }
                        }
                        item {
                            HorizontalDivider(
                                Modifier.fillMaxWidth(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            ) {
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.number.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeNumber(it)
                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.number_player),
                                )

                                Spacer(Modifier.size(52.dp))

                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.mpk.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeMPK(it)
                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.mpk),
                                )
                            }
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            ) {
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.height.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeHeight(it)

                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.height),
                                )

                                Spacer(Modifier.size(52.dp))

                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.chssPano.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeChssPano(it)

                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.chss_pano),
                                )
                            }
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            ) {
                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.weight.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeWeight(it)

                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.weight),
                                )

                                Spacer(Modifier.size(52.dp))

                                MaxiOutlinedTextField(
                                    value = state.sportsmanUI.chssPao.toStringWithCondition(),
                                    onValueChange = {
                                        viewModel.changeChssPao(it)

                                    },
                                    modifier = Modifier.weight(1f)
                                        .height(Constants.TextFieldHeight),
                                    placeholder = stringResource(Res.string.chss_pao),
                                )
                            }
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    MaxiOutlinedTextField(
                                        value = state.sportsmanUI.chssMax.toStringWithCondition(),
                                        onValueChange = {
                                            viewModel.changeChssMax(it)

                                        },
                                        modifier = Modifier.weight(1f)
                                            .height(Constants.TextFieldHeight),
                                        placeholder = stringResource(Res.string.chss_max),
                                    )
                                    Spacer(Modifier.size(20.dp))
                                    MaxiButton(
                                        shape = RoundedCornerShape(15.dp),
                                        onClick = {
                                            //todo
                                        },
                                        text = stringResource(Res.string.counting),
                                        modifier = Modifier.height(Constants.TextFieldHeight),
                                        buttonTextStyle = ButtonTextStyle.Medium
                                    )
                                }

                                Spacer(Modifier.size(52.dp))

                                MaxiOutlinedTextField(
                                    value = state.imt,
                                    onValueChange = {
                                        viewModel.changeImtString(it)

                                    },
                                    modifier = Modifier.weight(1f).height(Constants.TextFieldHeight)
                                        .onFocusChanged {
                                            if (!it.isFocused) {
                                                viewModel.changeImt()
                                            }
                                        },
                                    placeholder = stringResource(Res.string.imt),
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                                    .background(
                                        shape = RoundedCornerShape(15.dp),
                                        color = MaxiPulsTheme.colors.uiKit.grey400
                                    ).clip(
                                        RoundedCornerShape(
                                            15.dp
                                        )
                                    ), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.biometrics_indicators),
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ).weight(1f),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp
                                    )
                                )

                                Icon(
                                    painterResource(Res.drawable.right_ic),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 10.dp).size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )

                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                                    .background(
                                        shape = RoundedCornerShape(15.dp),
                                        color = MaxiPulsTheme.colors.uiKit.grey400
                                    ).clip(
                                        RoundedCornerShape(
                                            15.dp
                                        )
                                    ), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.sport_training_indicators),
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ).weight(1f),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp
                                    )
                                )

                                Icon(
                                    painterResource(Res.drawable.right_ic),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 10.dp).size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.size(5.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 20.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                MaxiButton(
                                    shape = RoundedCornerShape(15.dp),
                                    onClick = {
                                        rootNavigator.pop()
                                    },
                                    text = stringResource(Res.string.canceled),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaxiPulsTheme.colors.uiKit.grey400,
                                        contentColor = MaxiPulsTheme.colors.uiKit.textColor
                                    ),
                                    modifier = Modifier.height(54.dp)
                                )
                                Spacer(Modifier.size(20.dp))
                                MaxiButton(
                                    shape = RoundedCornerShape(15.dp),
                                    onClick = {
                                        viewModel.save()
                                    },
                                    text = stringResource(Res.string.save),
                                    modifier = Modifier.height(54.dp).weight(1f)
                                )
                            }
                        }
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Column(modifier = Modifier) {
                    Spacer(Modifier.size(20.dp))
                    Box(
                        modifier = Modifier.padding(end = 20.dp).size(40.dp)
                            .background(MaxiPulsTheme.colors.uiKit.primary, shape = CircleShape)
                            .clip(CircleShape).align(Alignment.End),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(Res.drawable.pencil),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).clickableBlank {
                                viewModel.changeSettingChssDialog()
                            },
                            tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                        )
                    }
                    Spacer(Modifier.size(11.dp))

                    Row(modifier = Modifier.weight(1f)) {
                        Spacer(Modifier.size(20.dp))
                        HeartBPMGraph(modifier = Modifier.width(150.dp))
                        Spacer(Modifier.size(20.dp))
                    }
                    Spacer(Modifier.size(20.dp))

                }
            }

            if (state.settingChssDialog) {
                var filterByAge by remember {
                    mutableStateOf(false)
                }
                var filterByThreshold by remember {
                    mutableStateOf(false)
                }
                MaxiAlertDialog(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).padding(50.dp),
                    onDismiss = { viewModel.changeSettingChssDialog() },
                    accept = { viewModel.changeSettingChssDialog() },
                    cancel = {
                        viewModel.changeSettingChssDialog()
                    },
                    title = stringResource(Res.string.settings_chss_zone),
                    acceptText = stringResource(Res.string.save),
                    cancelText = stringResource(Res.string.cancel),
                    alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                    descriptionContent = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f, false),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                                ) {
                                    MaxiCheckbox(checked = filterByAge, onCheckedChange = {
                                        filterByAge = !filterByAge
                                    }, modifier = Modifier.size(24.dp))
                                    Text(
                                        text = stringResource(Res.string.by_age),
                                        style = MaxiPulsTheme.typography.regular.copy(
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                            color = MaxiPulsTheme.colors.uiKit.textColor,
                                        )
                                    )
                                }

                                Row(
                                    modifier = Modifier.weight(1f, false),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                                ) {
                                    MaxiCheckbox(checked = filterByThreshold, onCheckedChange = {
                                        filterByThreshold = !filterByThreshold
                                    }, modifier = Modifier.size(24.dp))
                                    Text(
                                        text = stringResource(Res.string.by_zone_chss),
                                        style = MaxiPulsTheme.typography.regular.copy(
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                            color = MaxiPulsTheme.colors.uiKit.textColor,
                                        )
                                    )
                                }
                            }

                            ThresholdEditor(modifier = Modifier.weight(1f).fillMaxWidth())
                            Spacer(Modifier.size(60.dp))
                        }
                    },
                )
            }
            if (state.sensorAlertDialog) {
                SelectSensor(
                    onDismiss = {
                        viewModel.changeSensorAlertDialog()
                    },
                    observeSensor = {
                        viewModel.changeSensorAlertDialog()
                    },
                    sportsmanId = state.sportsmanUI.id,
                    sensor = state.sensorUI,
                    sensorAlreadyExit = {
                        false
                    },
                    accept = { sensor, sportsmanId ->
                        viewModel.changeSensor(
                            sensor,
                        )
                    }
                )
            }
            if (state.deleteSportsmanDialog) {
                MaxiAlertDialog(
                    modifier = Modifier.width(600.dp),
                    alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                    title = stringResource(Res.string.attention),
                    description = stringResource(Res.string.delete_sportsman_alert),
                    accept = {
                        viewModel.deleteSportsman(gamerId)
                    },
                    acceptText = stringResource(Res.string.ok),
                    cancelText = stringResource(Res.string.cancel),
                    cancel = {
                        viewModel.deleteSportsmanDialog()
                    },
                    onDismiss = {
                        viewModel.deleteSportsmanDialog()
                    }
                )
            }
        }

        if (openDialog.value) {
            val confirmEnabled = remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }
            DatePickerDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDate =
                                Instant.fromEpochMilliseconds(
                                    datePickerState.selectedDateMillis ?: 0
                                )
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                            val formattedMonth =
                                if (selectedDate.date.month.ordinal + 1 < 10) "0${selectedDate.date.month.ordinal + 1}" else "${selectedDate.date.month.ordinal + 1}"
                            val formattedDay =
                                if (selectedDate.date.dayOfMonth < 10) "0${selectedDate.date.dayOfMonth}" else "${selectedDate.date.dayOfMonth}"
                            openDialog.value = false
                            viewModel.changeDate(
                                year = selectedDate.date.year,
                                month = selectedDate.date.monthNumber,
                                day = selectedDate.date.dayOfMonth
                            )
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
                )
            }
        }

        ImagePicker(imagePickerShow) { image ->
            imagePickerPermission = false
            imagePickerShow = false
            val pathChosen = image ?: return@ImagePicker
            viewModel.changeAvatar(pathChosen.path)
        }
    }
}