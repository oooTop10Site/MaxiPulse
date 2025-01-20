package org.example.project.screens.tablet.tests.shuttleRun.result.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chss_pano
import maxipuls.composeapp.generated.resources.chss_pao
import maxipuls.composeapp.generated.resources.chss_peak
import maxipuls.composeapp.generated.resources.distance
import maxipuls.composeapp.generated.resources.duration
import maxipuls.composeapp.generated.resources.fio
import maxipuls.composeapp.generated.resources.marker_question
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.performance
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.training
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.maxOf
import org.example.project.ext.secondsToUI
import org.example.project.screens.tablet.tests.shuttleRun.result.ShuttleRunResultState
import org.example.project.screens.tablet.tests.shuttleRun.result.ShuttleRunResultViewModel
import org.example.project.screens.tablet.training.trainingResult.RegularResultBox
import org.example.project.screens.tablet.training.trainingResult.TitleResultBox
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun ColumnScope.OverallShuttleRunContent(
    state: ShuttleRunResultState,
    viewModel: ShuttleRunResultViewModel
) {
    Column() {
        Spacer(Modifier.size(20.dp))
        TopBarTitle(
            text = stringResource(Res.string.shuttle_run),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            showCurrentTime = true
        )
        Text(
            text = "${stringResource(Res.string.duration)}: ${
                state.sportsmans.maxOf(default = 0) { it.time }.orEmpty()
                    .secondsToUI()
            }",
            style = MaxiPulsTheme.typography.regular.copy(
                color = MaxiPulsTheme.colors.uiKit.textColor,
                fontSize = 14.sp,
                lineHeight = 14.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Visible,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 3.dp, start = 16.dp, end = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            MaxiOutlinedTextField(
                value = state.search,
                onValueChange = {
                    viewModel.changeSearch(it)
                    viewModel.search(it)
                },
                placeholder = stringResource(Res.string.search),
                modifier = Modifier
                    .height(Constants.TextFieldHeight)
                    .weight(1f),
                trailingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaxiPulsTheme.colors.uiKit.textColor
                    )
                }
            )
            Spacer(Modifier.size(20.dp))
            MaxiTextFieldMenu<String>(
                items = state.filters,
                itemToString = { it },
                currentValue = state.filter,
                onChangeWorkScope = {
                    viewModel.changeSelect(it)
                },
                modifier = Modifier.width(240.dp).height(Constants.TextFieldHeight),
                text = state.filter,
                placeholderText = ""
            )
            Spacer(Modifier.size(20.dp))
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape)
                    .background(MaxiPulsTheme.colors.uiKit.primary, CircleShape)
            ) {
                Icon(
                    painterResource(Res.drawable.marker_question),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).clickableBlank {
                        viewModel.changeDialog()
                    }.align(Alignment.Center),
                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                )
            }
        }
        Spacer(Modifier.size(20.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
            TitleResultBox(
                modifier = Modifier.width(150.dp).fillMaxHeight(),
                text = stringResource(Res.string.fio),
                maxLines = 2
            )
            val strings = listOf<StringResource>(
                Res.string.performance,
                Res.string.distance,
                Res.string.chss_peak,
                Res.string.mpk,
                Res.string.chss_pano,
                Res.string.chss_pao,
            )
            strings.forEach {
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                TitleResultBox(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.1f),
                    text = stringResource(it),
                    maxLines = 2
                )
            }

        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        LazyColumn(Modifier.weight(1f).fillMaxWidth()) {
            items(state.filterSportsmans) {
                CellItem(
                    sportsmanUI = it,
                    maxMpk = state.sportsmans.maxOf { it.mpk })
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
            }
        }
    }
}

@Composable
private fun CellItem(sportsmanUI: SportsmanShuttleRunResultUI, maxMpk: Int) {
    Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
        RegularResultBox(
            modifier = Modifier.width(150.dp).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.fio,
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = stringResource(sportsmanUI.performance.text),
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.distance.toString(),
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.heartRateMax.toString(),
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.mpk.toString(),
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.chssPano.toString(),
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanUI.chssPao.toString(),
            maxLines = 2
        )
    }
}