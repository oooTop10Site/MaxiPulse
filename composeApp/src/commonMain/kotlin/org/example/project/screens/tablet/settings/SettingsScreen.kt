package org.example.project.screens.tablet.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.settings
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.date_birthday
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.enter_data
import maxipuls.composeapp.generated.resources.general_info
import maxipuls.composeapp.generated.resources.high_performance_ble
import maxipuls.composeapp.generated.resources.lastname
import maxipuls.composeapp.generated.resources.middlename
import maxipuls.composeapp.generated.resources.name
import maxipuls.composeapp.generated.resources.sex
import maxipuls.composeapp.generated.resources.sport_category
import maxipuls.composeapp.generated.resources.sport_specialization
import maxipuls.composeapp.generated.resources.stage_sport_ready
import maxipuls.composeapp.generated.resources.use_route
import org.example.project.domain.model.setting.SettingTab
import org.example.project.screens.tablet.settings.edit.SettingEditScreen
import org.example.project.theme.uiKit.HeartRateGraph
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.utils.Constants

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            SettingsViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarTitle(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                text = stringResource(Res.string.settings),
                showCurrentTime = false,
                traingleIcon = {
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
                            modifier = Modifier.size(24.dp).clickableBlank {
                                navigator.push(SettingEditScreen())
                            },
                            tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                        )
                    }
                })
        }) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
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
                                        width = 108.dp,
                                        134.dp
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
                    }
                    Spacer(modifier = Modifier.size((30).dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "СШОР Гуляев Николай",
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 20.sp,
                                lineHeight = 26.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size((20).dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Аббривиатура",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.size((20).dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Вологда",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Spacer(Modifier.size(20.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                state.tabs.forEach { item ->
                    Spacer(Modifier.size(20.dp))
                    SettingSelectItem(
                        modifier = Modifier.fillMaxWidth(),
                        isSelect = state.selectTab?.let { it::class == item::class } == true,
                        item = item,
                        viewModel = viewModel
                    ) {
                        viewModel.changeSelectTab(item)
                    }
                    Spacer(Modifier.size(20.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
                Spacer(Modifier.size(30.dp))

                SettingsSelectableItem(
                    modifier = Modifier.fillMaxWidth(),
                    checked = state.useRoute,
                    text = stringResource(Res.string.use_route),
                    onChange = {
                        viewModel.changeUseRoute()
                    })
                Spacer(Modifier.size(20.dp))

                SettingsSelectableItem(
                    modifier = Modifier.fillMaxWidth(),
                    checked = state.useHighPerformance,
                    text = stringResource(Res.string.high_performance_ble),
                    onChange = {
                        viewModel.changeHighPerformance()
                    })
                Spacer(Modifier.size(30.dp))

            }
        }
    }
}

@Composable
internal fun SettingsSelectableItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    onChange: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(
            text = text, style = MaxiPulsTheme.typography.regular.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor,
            ), maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        MaxiSwitch(
            checked = checked,
            onCheckedChange = { onChange() },
            modifier = Modifier.width(69.dp).height(36.dp)
        )

    }
}

@Composable
private fun MinimumHeartRateContent(
    modifier: Modifier = Modifier,
    tab: SettingTab.MinimumHeartRate,
    viewModel: SettingsViewModel
) {
    Spacer(Modifier.size(10.dp))
    Row(modifier) {
        Row(
            modifier = Modifier.align(Alignment.Bottom),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.enter_data),
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                ),
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.size(20.dp))
            MaxiOutlinedTextField(
                value = tab.minimumHeartRateUI.minimun.toString(),
                modifier = Modifier.width(110.dp).height(
                    Constants.TextFieldHeight
                ),
                onValueChange = {
                    viewModel.selectMinHeartRate(it)
                }
            )
        }
        Spacer(Modifier.size(10.dp))
        HeartRateGraph(
            showY = false,
            modifier = Modifier.weight(1f).height(180.dp),
            heartRateData = tab.minimumHeartRateUI.heartRate
        )
    }
}

@Composable
private fun SelectFormulaMaxHeartRateContent(
    modifier: Modifier = Modifier,
    tab: SettingTab.SelectFormulaMaxHeartRate,
    viewModel: SettingsViewModel
) {
    Column(modifier = modifier) {
        Spacer(Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(15.dp)).border(
                width = 1.dp,
                color = MaxiPulsTheme.colors.uiKit.divider,
                shape = RoundedCornerShape(15.dp)
            )
        ) {
            tab.maxHeartRateUI.items.forEachIndexed { index, it ->
                if (index != 0) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                        .clickableBlank { viewModel.selectFormulaFormulaChssMax(it) }) {
                    Text(
                        text = it.title,
                        style = MaxiPulsTheme.typography.regular.copy(
                            fontSize = 13.sp,
                            lineHeight = 13.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(270.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    VerticalDivider(
                        Modifier.fillMaxHeight(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Box(modifier = Modifier.weight(1f).padding(horizontal = 90.dp),) {
                        Text(
                            text = it.desc,
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 13.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    VerticalDivider(
                        Modifier.fillMaxHeight(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Box(modifier = Modifier.fillMaxHeight().width(130.dp), contentAlignment = Alignment.Center) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(24.dp).clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                                )
                        ) {
                            if (tab.maxHeartRateUI.selectItem == it) {
                                Box(
                                    modifier = Modifier.size(16.dp)
                                        .clip(RoundedCornerShape(5.dp)).background(
                                            color = MaxiPulsTheme.colors.uiKit.primary,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SportsmanProfileContent(
    modifier: Modifier = Modifier,
    tab: SettingTab.SettingsSportsman,
    viewModel: SettingsViewModel
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Spacer(Modifier.size(22.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SelectableCheckBoxItem(
                modifier = Modifier.width(300.dp),
                checked = tab.generalInfo.lastname,
                text = stringResource(Res.string.lastname)
            ) {
                viewModel.selectLastName()
            }
            Spacer(Modifier.weight(1f))
            SelectableCheckBoxItem(
                modifier = Modifier.width(300.dp),
                checked = tab.generalInfo.stageSportReadiness,
                text = stringResource(Res.string.stage_sport_ready)
            ) {
                viewModel.selectStageReadiness()
            }
            Spacer(Modifier.weight(1f))
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SelectableCheckBoxItem(
                modifier = Modifier.width(300.dp),
                checked = tab.generalInfo.birthday,
                text = stringResource(Res.string.date_birthday)
            ) {
                viewModel.selectBirthday()
            }
            Spacer(Modifier.weight(1f))
            SelectableCheckBoxItem(
                modifier = Modifier.width(300.dp),
                checked = tab.generalInfo.sportCategory,
                text = stringResource(Res.string.sport_category)
            ) {
                viewModel.selectSportCategory()
            }
            Spacer(Modifier.weight(1f))
        }
        SelectableCheckBoxItem(
            modifier = Modifier.width(300.dp),
            checked = tab.generalInfo.sex,
            text = stringResource(Res.string.sex)
        ) {
            viewModel.selectSex()
        }
        Text(
            style = MaxiPulsTheme.typography.semiBold.copy(
                color = MaxiPulsTheme.colors.uiKit.textColor,
                fontSize = 16.sp,
                lineHeight = 16.sp
            ),
            text = stringResource(Res.string.general_info),
            modifier = Modifier.fillMaxWidth()
        )
        SelectableCheckBoxItem(
            modifier = Modifier.width(300.dp),
            checked = tab.generalInfo.name,
            text = stringResource(Res.string.name)
        ) {
            viewModel.selectName()
        }
        SelectableCheckBoxItem(
            modifier = Modifier.width(300.dp),
            checked = tab.generalInfo.middleName,
            text = stringResource(Res.string.middlename)
        ) {
            viewModel.selectMiddleName()
        }
        SelectableCheckBoxItem(
            modifier = Modifier.width(300.dp),
            checked = tab.generalInfo.sportSpecialization,
            text = stringResource(Res.string.sport_specialization)
        ) {
            viewModel.selectSportSpecialization()
        }

    }
}

@Composable
private fun SelectableCheckBoxItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    onChange: () -> Unit,
) {

    Row(modifier = modifier.clickableBlank {
        onChange()
    }, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaxiPulsTheme.typography.regular.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor,
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        MaxiCheckbox(
            modifier = Modifier.size(24.dp),
            checked = checked,
            onCheckedChange = { onChange() })


    }

}

@Composable
internal fun SettingSelectItem(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel?,
    item: SettingTab,
    isSelect: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.animateContentSize().padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickableBlank {
                onClick()
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(item.text), style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                ), maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val rotationAngle by animateFloatAsState(
                targetValue = if (isSelect) 0f else -90f,
                animationSpec = tween(durationMillis = 300) // Длительность анимации в миллисекундах
            )

            Icon(
                painter = painterResource(Res.drawable.drop_ic),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotationAngle),
                tint = MaxiPulsTheme.colors.uiKit.textColor
            )

        }

        if (isSelect && viewModel!= null) {
            when (item) {
                SettingTab.AlgorithmAnaerobicThreshold -> {

                }

                SettingTab.MethodCountTrimp -> {

                }

                is SettingTab.MinimumHeartRate -> {
                    MinimumHeartRateContent(modifier = Modifier.fillMaxWidth(), item, viewModel)
                }

                is SettingTab.SelectFormulaMaxHeartRate -> {
                    SelectFormulaMaxHeartRateContent(
                        modifier = Modifier.fillMaxWidth(),
                        item,
                        viewModel = viewModel
                    )
                }

                is SettingTab.SettingsSportsman -> {
                    SportsmanProfileContent(
                        modifier = Modifier.fillMaxWidth(),
                        item,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}