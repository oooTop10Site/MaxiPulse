package org.example.project.screens.tablet.options.utp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
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
import maxipuls.composeapp.generated.resources.microcycle
import maxipuls.composeapp.generated.resources.period_readiness
import maxipuls.composeapp.generated.resources.stage_readiness
import maxipuls.composeapp.generated.resources.start_tarining
import maxipuls.composeapp.generated.resources.utp_title
import maxipuls.composeapp.generated.resources.year_education
import org.example.project.ext.clickableBlank
import org.example.project.ext.toByText
import org.example.project.ext.toText
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.group.components.CompositionCard
import org.example.project.screens.tablet.group.groupDetail.GroupDetailScreen
import org.example.project.screens.tablet.group.groupEdit.GroupEditScreen
import org.example.project.screens.tablet.tests.TestItem
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.stringResource

class UtpScreen : Screen {

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
                    Column(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            //фильтры
                        }
                        Spacer(Modifier.size(70.dp))
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth()
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
                                    text = stringResource(state.currentDate.month.toText()),
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
                                            )
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
                            println("massive huinu - ${state.daysDate}")
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
                                    chunk.forEachIndexed { index, date ->
                                        val isSelect = date == state.currentDate
                                        Box(
                                            modifier = Modifier.weight(1f).fillMaxHeight(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Box(
                                                modifier = Modifier.then(
                                                    if (isSelect) Modifier.size(
                                                        50.dp
                                                    ).background(
                                                        color = MaxiPulsTheme.colors.uiKit.primary,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) else Modifier
                                                ), contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = date.dayOfMonth.toString(),
                                                    modifier = Modifier,
                                                    textAlign = TextAlign.Center,
                                                    style = MaxiPulsTheme.typography.bold.copy(
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 16.sp,
                                                        lineHeight = 16.sp,
                                                        color = if (isSelect) MaxiPulsTheme.colors.uiKit.white else MaxiPulsTheme.colors.uiKit.textColor.copy(
                                                            alpha = if (date.month != state.currentDate.month) 0.3f else 1f
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
                        Spacer(Modifier.size(70.dp))
                        MaxiButton(
                            onClick = debouncedClick() {

                            },
                            text = stringResource(Res.string.start_tarining),
                            modifier = Modifier.height(69.dp).width(416.dp)
                        )
                        Spacer(Modifier.size(20.dp))
                    }
                }


            }
        }
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