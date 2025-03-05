package org.example.project.screens.tablet.options.utp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.datetime.DayOfWeek
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.calendar
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.criteria_upload
import maxipuls.composeapp.generated.resources.duration_stage_min
import maxipuls.composeapp.generated.resources.event
import maxipuls.composeapp.generated.resources.is_compare_with_plan
import maxipuls.composeapp.generated.resources.month
import maxipuls.composeapp.generated.resources.period
import maxipuls.composeapp.generated.resources.planned_training_effect
import maxipuls.composeapp.generated.resources.result_training_effect
import maxipuls.composeapp.generated.resources.save
import maxipuls.composeapp.generated.resources.stage_readiness
import maxipuls.composeapp.generated.resources.type_event
import maxipuls.composeapp.generated.resources.upload_in_percent_by_chss_max
import maxipuls.composeapp.generated.resources.week
import maxipuls.composeapp.generated.resources.year_readiness
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.log.CriteriaUpload
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.utp.UTPTab
import org.example.project.ext.clickableBlank
import org.example.project.ext.toText
import org.example.project.ext.toUI
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.group.components.CompositionCard
import org.example.project.screens.tablet.group.groupEdit.GroupEditScreen
import org.example.project.screens.tablet.tests.SelectableDefaultItem
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.AlertButtons
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.mic
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.skipment
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.start_go
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.training_stage
import maxipuls.composeapp.generated.resources.trash
import org.example.project.domain.model.AnalizeGraph
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.domain.model.training.TrainingUtpUI
import org.example.project.ext.granted
import org.example.project.ext.toTextShort
import org.example.project.ext.toTrainingStageChssUI
import org.example.project.platform.SpeechToTextRecognizer
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.screens.adaptive.mainTab.tabs.MainTab
import org.example.project.screens.tablet.options.utp.graphs.GrowthGraph
import org.example.project.screens.tablet.options.utp.graphs.LoadGraph
import org.example.project.screens.tablet.options.utp.graphs.MonotonyGraph
import org.example.project.screens.tablet.options.utp.graphs.TensionGraph
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class UtpScreen :   Screen, KoinComponent {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            UtpViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val buttonDivision = when (screenSize.heightSizeClass) {
            WindowHeightSizeClass.Medium -> 1.4
            WindowHeightSizeClass.Expanded -> 1.0
            WindowHeightSizeClass.Compact -> 1.7
            else -> 1.5
        }
        LaunchedEffect(viewModel) {
            viewModel.loadGroups()
        }
        MaxiPageContainer() {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(0.25f)) {
                    Spacer(Modifier.size(22.dp))
                    BackIcon(modifier = Modifier.padding(start = 20.dp).size(40.dp)) {
                        rootNavigator.pop()
                    }
                    Spacer(Modifier.size(10.dp))

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.groups) {
                            CompositionCard(
                                modifier = Modifier.weight(1f).height(100.dp),
                                title = it.title,
                                members = it.member,
                                onClick = {
                                    viewModel.selectGroup(it)
                                },
                                isSelect = state.selectGroup == it,
                                isEdit = false,
                                onEdit = debouncedClick() {
                                    rootNavigator.push(GroupEditScreen(groupId = it.id))
                                }
                            )
                        }
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Column(modifier = Modifier.weight(0.75f)) {
                    Row(
                        modifier = Modifier.height(60.dp).fillMaxWidth()
                            .background(color = MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.4f)),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        state.tabs.forEach {
                            val isSelect = state.utpTab == it
                            Column(
                                modifier = Modifier.weight(1f).height(50.dp)
                                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                                    .align(
                                        Alignment.Bottom
                                    )
                                    .background(color = if (isSelect) MaxiPulsTheme.colors.uiKit.white else androidx.compose.ui.graphics.Color.Transparent)
                                    .clickableBlank {
                                        viewModel.changeUTPTab(it)
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(Modifier.size(8.dp))
                                Text(
                                    text = stringResource(it.title),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 20.sp,
                                        lineHeight = 20.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = if (isSelect) MaxiPulsTheme.colors.uiKit.textColor else MaxiPulsTheme.colors.uiKit.lightTextColor,
                                    modifier = Modifier
                                )

                                Spacer(Modifier.weight(1f))

                                if (isSelect) {
                                    Box(
                                        modifier = Modifier.width(213.dp).height(5.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(color = MaxiPulsTheme.colors.uiKit.primary)
                                    )
                                    Spacer(Modifier.size(3.dp))
                                }
                            }
                        }
                    }
                    if (state.selectGroup != null) {
                        when (state.utpTab) {
                            UTPTab.PannedUtp -> {
                                PlannedUtpConetent(state.selectGroup, viewModel, state)
                            }

                            UTPTab.AnalizeUtp -> {
                                AnalizeUtpConetent(state.selectGroup, viewModel, state)

                            }
                        }
                    }
                }
            }
        }
        if (state.selectedTrainingUtpUI != null) {
            PlannedTraining(viewModel, state)
        }
    }

    @Composable
    private fun ColumnScope.AnalizeUtpConetent(
        selectGroup: GroupUI?,
        viewModel: UtpViewModel,
        state: UtpState
    ) {

        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.week),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
                Spacer(Modifier.size(15.dp))
                MaxiSwitch(checked = state.isWeek, onCheckedChange = {
                    viewModel.changeIsWeek()
                })
                Spacer(Modifier.size(15.dp))
                Text(
                    text = stringResource(Res.string.month),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
                Spacer(Modifier.size(90.dp))

                MaxiTextFieldMenu<String>(
                    currentValue = state.period,
                    text = state.period,
                    onChangeWorkScope = {
                        viewModel.changePeriod(
                            value = it
                        )
                    },
                    items = state.periods,
                    itemToString = { it },
                    modifier = Modifier.height(Constants.TextFieldHeight)
                        .weight(1f),
                    placeholderText = stringResource(Res.string.period)
                )
                Spacer(Modifier.size(20.dp))

                Icon(
                    painter = painterResource(Res.drawable.calendar),
                    modifier = Modifier.size(30.dp),
                    contentDescription = null,
                    tint = MaxiPulsTheme.colors.uiKit.textColor
                )
            }
            var width by remember {
                mutableStateOf(0.dp)
            }
            Column(modifier = Modifier.weight(1f).padding(end = 20.dp)) {
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    BoxWithConstraints(modifier = Modifier.weight(1f)) {
                        width = maxWidth
                        when (state.selectAnalizeGraph) {
                            AnalizeGraph.MONOTONY -> MonotonyGraph(modifier = Modifier.fillMaxSize())
                            AnalizeGraph.GROWTH -> GrowthGraph()
                            AnalizeGraph.LOAD -> LoadGraph()
                            AnalizeGraph.TENSION -> TensionGraph(modifier = Modifier.fillMaxSize())
                            null -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                        .padding(start = 112.dp, end = 40.dp).border(
                                            width = 1.dp,
                                            color = MaxiPulsTheme.colors.uiKit.divider,
                                            shape = RoundedCornerShape(
                                                topStart = 25.dp,
                                                bottomStart = 25.dp
                                            )
                                        )
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxHeight().border(
                            width = 1.dp,
                            color = MaxiPulsTheme.colors.uiKit.divider,
                            shape = RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                        ).padding(start = 30.dp, end = 20.dp),
                        contentPadding = PaddingValues(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.analizeGraphTabs) {
                            SelectableDefaultItem(
                                modifier = Modifier.width(170.dp).height(37.dp),
                                title = stringResource(it.title),
                                onClick = {
                                    viewModel.changeSelectAnalizeGraph(it)
                                },
                                isPay = false,
                                isSelect = it == state.selectAnalizeGraph
                            )
                        }

                    }

                }
                Spacer(Modifier.size(20.dp))
                Row(
                    modifier = Modifier.width(width).padding(start = 72.dp)
                        .padding(horizontal = 54.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    state.currentWeek.forEach { item ->
                        Text(
                            text = "${
                                item.toUI().split(".").dropLast(1).joinToString(".")
                            }\n${stringResource(item.dayOfWeek.toTextShort()).uppercase()}",
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                textAlign = TextAlign.Center,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }


                }
            }

            Spacer(Modifier.size(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.is_compare_with_plan),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
                Spacer(Modifier.size(15.dp))
                MaxiSwitch(checked = state.isCompareWithPlan, onCheckedChange = {
                    viewModel.changeIsComposeWithPlan()
                })
            }
            Spacer(Modifier.size(20.dp))

        }
    }

}

@Composable
private fun ColumnScope.PlannedUtpConetent(
    selectGroup: GroupUI?,
    viewModel: UtpViewModel,
    state: UtpState
) {
    val tabNavigator = LocalTabNavigator.current
    val navigator = RootNavigator.currentOrThrow
    Column(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.stage_readiness),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(40.dp))


                MaxiTextFieldMenu<String>(
                    currentValue = selectGroup?.selectTrainingStage.orEmpty(),
                    text = selectGroup?.selectTrainingStage.orEmpty(),
                    onChangeWorkScope = {
                        viewModel.changeSelectTrainingStage(
                            id = state.selectedTrainingUtpUI?.id.orEmpty(),
                            value = it
                        )
                    },
                    items = state.trainingStages,
                    itemToString = { it },
                    modifier = Modifier.height(Constants.TextFieldHeight)
                        .weight(1f),
                    placeholderText = ""
                )
            }
            Spacer(Modifier.size(24.dp))
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.year_readiness),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(40.dp))

                MaxiTextFieldMenu<String>(
                    currentValue = selectGroup?.yearReadies.orEmpty(),
                    text = selectGroup?.yearReadies.orEmpty(),
                    onChangeWorkScope = {
                        viewModel.changeSelectYearReadies(
                            id = state.selectedTrainingUtpUI?.id.orEmpty(),
                            value = it
                        )
                    },
                    items = state.yearsReadies,
                    itemToString = { it },
                    modifier = Modifier.height(Constants.TextFieldHeight)
                        .weight(1f),
                    placeholderText = ""
                )
            }
        }
        Spacer(Modifier.size(60.dp))
        Column(
            modifier = Modifier.height(330.dp).fillMaxWidth()
                .padding(horizontal = 20.dp).border(
                    shape = RoundedCornerShape(25.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider,
                    width = 1.dp
                ).clip(RoundedCornerShape(25.dp))
        ) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(state.currentDay.month.toText()),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
            }

            HorizontalDivider(
                Modifier.fillMaxWidth(),
                color = MaxiPulsTheme.colors.uiKit.divider,
                thickness = 1.dp
            )
            val daysOfWeek = DayOfWeek.entries
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                daysOfWeek.forEachIndexed { index, dayOfWeek ->
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(dayOfWeek.toText()),
                            modifier = Modifier,
                            style = MaxiPulsTheme.typography.medium.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor

                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (index != daysOfWeek.lastIndex) {
                        VerticalDivider(
                            Modifier.fillMaxHeight(),
                            color = MaxiPulsTheme.colors.uiKit.divider,
                            thickness = 1.dp
                        )
                    }
                }
            }
            state.daysDate.chunked(7).forEach { chunk ->
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider,
                    thickness = 1.dp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                ) {
                    chunk.forEachIndexed { index, utpTraining ->
                        val isSelect = utpTraining.date == state.selectedDay?.first
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight()
                                .clickableBlank {
                                    viewModel.changeSelectDay(utpTraining.date)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier.then(
                                    if (isSelect) Modifier.size(
                                        40.dp
                                    ).background(
                                        color = MaxiPulsTheme.colors.uiKit.primary,
                                        shape = RoundedCornerShape(10.dp)
                                    ) else Modifier
                                ), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = utpTraining.date.dayOfMonth.toString(),
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        color = if (isSelect) MaxiPulsTheme.colors.uiKit.white else MaxiPulsTheme.colors.uiKit.textColor.copy(
                                            alpha = if (utpTraining.date.month != state.currentDay.month) 0.3f else 1f
                                        )
                                    )
                                )
                            }
                        }
                        if (index != chunk.lastIndex) {
                            VerticalDivider(
                                Modifier.fillMaxHeight(),
                                color = MaxiPulsTheme.colors.uiKit.divider,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }

        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(state.selectedDay?.second.orEmpty()) { index, it ->
                TrainingComponent(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    onStart = debouncedClick() {
                        tabNavigator.current = MainTab(
                            stages = (it.stages.map { it.toTrainingStageChssUI() })
                        )
                        navigator.popUntilRoot()
                    },
                    trainingUtpStageUI = it,
                    onEdit = {
                        viewModel.changeSelectTrainingUTPUI(it)
                    },
                    onDelete = {
                        viewModel.deleteSelectTrainingUTPUI(it)
                    },
                    number = (index + 1).toString(),
                    isEnd = (state.selectedDay?.first ?: state.currentDay) < state.currentDay
                )
            }
        }
        state.selectedDay?.let {
            if (state.selectedDay.first >= state.currentDay) {
                Box(
                    modifier = Modifier.size(50.dp)
                        .background(
                            MaxiPulsTheme.colors.uiKit.primary,
                            shape = CircleShape
                        )
                        .clip(CircleShape).clickableBlank() {
                            viewModel.addEmptyTrainingUtp()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(Res.drawable.add_ic),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                    )
                }
                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

@Composable
internal fun TrainingComponent(
    modifier: Modifier = Modifier,
    number: String,
    trainingUtpStageUI: TrainingUtpUI,
    isEnd: Boolean,
    onStart: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Box(
        modifier.background(
            color = if (isEnd) MaxiPulsTheme.colors.uiKit.card else MaxiPulsTheme.colors.uiKit.trainingCardColor,
            shape = RoundedCornerShape(25.dp)
        ), contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(20.dp))
            Text(
                text = "${stringResource(Res.string.training)} $number",
                style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Spacer(Modifier.size(20.dp))
            VerticalDivider(
                modifier = Modifier.height(17.dp),
                color = MaxiPulsTheme.colors.uiKit.textFieldStroke
            )
            Spacer(Modifier.size(20.dp))
            Text(
                text = trainingUtpStageUI.date.toUI(),
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Spacer(Modifier.size(40.dp))
            if (isEnd) {
                Box(modifier = Modifier.height(40.dp).weight(1f)) {
                    Box(
                        modifier = Modifier.height(35.dp)
                            .fillMaxWidth(trainingUtpStageUI.expectValue)
                            .background(
                                color = Color(0xFFC0DEFD),
                                shape = RoundedCornerShape(100.dp)
                            )
                    )
                    Box(
                        modifier = Modifier.height(35.dp)
                            .fillMaxWidth(trainingUtpStageUI.existValue)
                            .background(
                                color = Color(0xFFF8BBCF),
                                shape = RoundedCornerShape(100.dp)
                            )
                    )
                }
            } else {
                MaxiButton(
                    onClick = debouncedClick() {
                        onStart()
                    },
                    text = stringResource(Res.string.start_go),
                    modifier = Modifier.height(40.dp).weight(1f)
                )
            }
            Spacer(Modifier.size(40.dp))
            if (!isEnd) {
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(
                            MaxiPulsTheme.colors.uiKit.primary,
                            shape = CircleShape
                        )
                        .clip(CircleShape).clickableBlank() {
                            onEdit()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(Res.drawable.pencil),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                    )
                }
                Spacer(Modifier.size(20.dp))
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(
                            MaxiPulsTheme.colors.uiKit.white,
                            shape = CircleShape
                        )
                        .clip(CircleShape).clickableBlank() {
                            onDelete()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(Res.drawable.trash),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaxiPulsTheme.colors.uiKit.primary
                    )
                }
            } else {
                if (trainingUtpStageUI.existValue == 0f && trainingUtpStageUI.expectValue == 0f) {
                    Text(
                        text = stringResource(Res.string.skipment),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 24.sp,
                            lineHeight = 24.sp,
                            color = MaxiPulsTheme.colors.uiKit.textDropDown
                        )
                    )
                }
            }
            Spacer(Modifier.size(20.dp))

        }
    }

}

@Composable
internal fun KoinComponent.PlannedTraining(viewModel: UtpViewModel, state: UtpState) {
    val scrollState = rememberLazyListState()
    var showRecord by remember { mutableStateOf(false) }
    var isRecording by remember { mutableStateOf(false) }
    var recognizedText by remember { mutableStateOf("") }

    var audioPermission by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.speechRecognizer.setOnResultListener { text ->
            recognizedText = text
        }
        viewModel.speechRecognizer.setOnPartialResultListener { text ->
            recognizedText = text
        }
    }
    LaunchedEffect(Unit) {
        if (state.selectedTrainingUtpUI?.stages.orEmpty().isNotEmpty()) {
            scrollState.animateScrollToItem(state.selectedTrainingUtpUI?.stages?.lastIndex ?: 0)
        }
    }
    state.selectedTrainingUtpUI?.let {
        MaxiAlertDialog(
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f),
            paddingValues = PaddingValues(
                top = 20.dp,
                bottom = 40.dp,
                start = 40.dp,
                end = 40.dp
            ),
            onDismiss = {
                viewModel.dismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(
                    rememberScrollState(0)
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.planned_training_effect),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = "${stringResource(state.selectedTrainingUtpUI.date.dayOfWeek.toText()).lowercase()} (${state.selectedTrainingUtpUI.date.toUI()})",
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.size(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(260.dp),
                        text = stringResource(Res.string.type_event),
                        style = MaxiPulsTheme.typography.medium.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    MaxiTextFieldMenu<EventType>(
                        currentValue = state.selectedTrainingUtpUI.typeOfEvent,
                        text = state.selectedTrainingUtpUI.typeOfEvent.title,
                        onChangeWorkScope = {
                            viewModel.changeSelectedEvent(it)
                        },
                        items = state.events,
                        itemToString = { it.title },
                        modifier = Modifier.height(Constants.TextFieldHeight)
                            .weight(1f),
                        placeholderText = stringResource(Res.string.event)
                    )


                }

                Spacer(Modifier.size(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(260.dp),
                        text = stringResource(Res.string.criteria_upload),
                        style = MaxiPulsTheme.typography.medium.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    MaxiTextFieldMenu<CriteriaUpload>(
                        currentValue = state.selectedTrainingUtpUI.criteriaUpload,
                        text = state.selectedTrainingUtpUI.criteriaUpload.title,
                        onChangeWorkScope = {
                            viewModel.changeSelectedCriteria(it)
                        },
                        items = state.criteriaUploads,
                        itemToString = { it.title },
                        modifier = Modifier.height(Constants.TextFieldHeight)
                            .weight(1f),
                        placeholderText = stringResource(Res.string.criteria_upload)
                    )
                }

                Spacer(Modifier.size(20.dp))

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider,
                    thickness = 1.dp
                )


                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.heightIn(max = 288.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(state.selectedTrainingUtpUI.stages) { index, item ->
                        println("item - $item")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.width(260.dp),
                                text = "${stringResource(Res.string.training_stage)} ${index + 1}",
                                style = MaxiPulsTheme.typography.medium.copy(
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(Res.string.duration_stage_min),
                                    style = MaxiPulsTheme.typography.regular.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        color = MaxiPulsTheme.colors.uiKit.placeholder
                                    ),
                                    modifier = Modifier.padding(start = 14.dp, bottom = 10.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                MaxiOutlinedTextField(
                                    value = item.min.toString(),
                                    modifier = Modifier
                                        .height(Constants.TextFieldHeight),
                                    placeholder = "",
                                    onValueChange = {
                                        viewModel.changeSelectedMin(
                                            min = it,
                                            trainingUtpStageId = item.id
                                        )
                                    })
                            }

                            Spacer(Modifier.size(20.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(Res.string.upload_in_percent_by_chss_max),
                                    style = MaxiPulsTheme.typography.regular.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        color = MaxiPulsTheme.colors.uiKit.placeholder
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 14.dp, bottom = 10.dp)
                                )
                                MaxiOutlinedTextField(
                                    value = item.value.toString(),
                                    modifier = Modifier
                                        .height(Constants.TextFieldHeight),
                                    placeholder = "",
                                    onValueChange = {
                                        viewModel.changeSelectedValue(
                                            value = it,
                                            trainingUtpStageId = item.id
                                        )
                                    })
                            }
                        }
                    }

                    item {
                        println("state.days - ${state.selectedTrainingUtpUI.stages}")
                        val enable =
                            state.selectedTrainingUtpUI.stages.all { it.value != 0 && it.min != 0 }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(80.dp)
                                    .background(
                                        if (enable) MaxiPulsTheme.colors.uiKit.primary else MaxiPulsTheme.colors.uiKit.grey500,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape).clickableBlank(enabled = enable) {
                                        viewModel.addEmptyStage()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(Res.drawable.add_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(44.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }
                            val scope = rememberCoroutineScope()
                            viewModel.audioPermissionsService.checkPermissionFlow(Permission.RECORD_AUDIO)
                                .collectAsState(
                                    viewModel.audioPermissionsService.checkPermission(
                                        Permission.RECORD_AUDIO
                                    )
                                )
                                .granted {
                                    audioPermission = true
                                }

                            if (!showRecord) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(80.dp)
                                        .background(
                                            if (enable) MaxiPulsTheme.colors.uiKit.primary else MaxiPulsTheme.colors.uiKit.grey500,
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape).clickableBlank(
                                            enabled = enable,
                                            onClick = debouncedClick() {
                                                recognizedText = ""
                                                if (audioPermission) {
                                                    showRecord = !showRecord
                                                } else {
                                                    scope.launch {
                                                        if (viewModel.audioPermissionsService.checkPermission(
                                                                Permission.RECORD_AUDIO
                                                            )
                                                                .granted()
                                                        ) {
                                                            viewModel.speechRecognizer.startListening()
                                                            audioPermission = true
                                                        } else {
                                                            viewModel.audioPermissionsService.providePermission(
                                                                Permission.RECORD_AUDIO
                                                            )
                                                        }

                                                    }
                                                }

                                            }),
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.mic),
                                        modifier = Modifier.size(44.dp),
                                        tint = MaxiPulsTheme.colors.uiKit.white,
                                        contentDescription = if (showRecord) "Stop" else "Mic"
                                    )
                                }
                            }
                        }
                    }

                }

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider,
                    thickness = 1.dp
                )

                Spacer(Modifier.size(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.result_training_effect),
                        style = MaxiPulsTheme.typography.medium.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        modifier = Modifier.width(225.dp)
                    )
                    Spacer(Modifier.size(40.dp))
                    Text(
                        text = "100 (Единицы ТРИМП)",
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        modifier = Modifier
                    )
                }
                Spacer(Modifier.size(50.dp))

                AlertButtons(
                    modifier = Modifier.fillMaxWidth(),
                    acceptText = stringResource(Res.string.save),
                    cancelText = stringResource(Res.string.cancel),
                    cancel = {
                        viewModel.dismiss()
                    },
                    accept = {
                        viewModel.saveSelectedTrainingUtp(state.selectedTrainingUtpUI)
                    }
                )
            }
        }
    }

    if (showRecord) {
        MaxiAlertDialog(
            alertDialogButtons = if (isRecording) MaxiAlertDialogButtons.Accept else null,
            modifier = Modifier.width(300.dp).animateContentSize(),
            paddingAfterTitle = false,
            title = null,
            acceptText = stringResource(Res.string.ok),
            accept = {
                isRecording = !isRecording
                showRecord = !showRecord
                println("acceptText - $recognizedText")
                viewModel.speechRecognizer.stopListening()
                viewModel.trainingStages(recognizedText)
            },
            cancelText = null,
            cancel = {
                isRecording = !isRecording
                showRecord = !showRecord
                viewModel.speechRecognizer.stopListening()
            },
            descriptionContent = {
                Column(modifier = Modifier) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Пример ввода: Тренировка состоит из 3 частей,\nПервая - продолжительность 10 минут  ЧСС пик 165.\nВторая - продолжительность 20 минут  ЧСС пик 192.\nТретья - продолжительность 30 минут  ЧСС пик 202. ",
                        style = MaxiPulsTheme.typography.regular.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 13.sp,
                            lineHeight = 16.sp,
                        )
                    )

                    Spacer(Modifier.size(40.dp))
                    if (isRecording) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = if (recognizedText.isBlank()) "Говорите..." else recognizedText,
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 16.sp,
                                lineHeight = 18.sp,
                            ),
                            textAlign = if (recognizedText.isBlank()) TextAlign.Center else TextAlign.Start
                        )
                    } else {
                        FloatingActionButton(
                            containerColor = MaxiPulsTheme.colors.uiKit.primary,
                            contentColor = MaxiPulsTheme.colors.uiKit.white,
                            modifier = Modifier.padding(vertical = 20.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = debouncedClick() {
                                isRecording = !isRecording
                                viewModel.speechRecognizer.startListening()
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.mic),
                                contentDescription = if (showRecord) "Stop" else "Mic"
                            )
                        }
                    }
                    Spacer(Modifier.size(40.dp))
                }
            },
            onDismiss = {
                isRecording = !isRecording
                showRecord = !showRecord
                viewModel.speechRecognizer.stopListening()
            })
    }
}

@Composable
private fun SelectableItem(
    modifier: Modifier = Modifier,
    value: Boolean,
    onChange: () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.clickableBlank {
            onChange()
        },
    ) {
        Text(
            text = text,
            style = MaxiPulsTheme.typography.regular.copy(
                color = MaxiPulsTheme.colors.uiKit.textColor,
                fontSize = 14.sp,
                lineHeight = 14.sp
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        MaxiCheckbox(checked = value, onCheckedChange = {
            onChange()
        }, modifier = Modifier.size(24.dp))
    }

}