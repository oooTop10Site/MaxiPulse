package org.example.project.screens.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.ending_training
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.settings
import org.example.project.domain.model.ButtonActions
import org.example.project.ext.clickableBlank
import org.example.project.screens.group.groupEdit.GroupEditScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.rotate
import maxipuls.composeapp.generated.resources.arrow_right
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.high_performance_ble
import maxipuls.composeapp.generated.resources.use_route
import org.example.project.domain.model.setting.SettingTab
import org.example.project.theme.uiKit.MaxiSwitch

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
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
                state.tabs.forEach {
                    Spacer(Modifier.size(20.dp))
                    SettingItem(
                        modifier = Modifier.fillMaxWidth(),
                        isSelect = state.selectTab == it,
                        item = it
                    ) {
                        viewModel.changeSelectTab(it)
                    }
                    Spacer(Modifier.size(20.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
                Spacer(Modifier.size(30.dp))

                SelectableItem(modifier = Modifier.fillMaxWidth(), checked = state.useRoute, text = stringResource(Res.string.use_route), onChange = {
                    viewModel.changeUseRoute()
                })
                Spacer(Modifier.size(20.dp))

                SelectableItem(modifier = Modifier.fillMaxWidth(), checked = state.useHighPerformance,text = stringResource(Res.string.high_performance_ble), onChange = {
                    viewModel.changeHighPerformance()
                })
                Spacer(Modifier.size(30.dp))

            }
        }
    }
}

@Composable
private fun SelectableItem(
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
private fun SettingItem(
    modifier: Modifier = Modifier,
    item: SettingTab,
    isSelect: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.animateContentSize().clickableBlank {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
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

        if (isSelect) {
            Spacer(Modifier.size(20.dp))
            Text(text = "CONTENT")
        }
    }
}