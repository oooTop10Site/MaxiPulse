package org.example.project.screens.tablet.training.trainingResult.contents

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.duration
import maxipuls.composeapp.generated.resources.equal
import maxipuls.composeapp.generated.resources.fio
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.trimp
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.ext.maxOf
import org.example.project.ext.secondsToUI
import org.example.project.screens.tablet.training.trainingResult.RegularResultBox
import org.example.project.screens.tablet.training.trainingResult.TitleResultBox
import org.example.project.screens.tablet.training.trainingResult.TrainingResultState
import org.example.project.screens.tablet.training.trainingResult.TrainingResultViewModel
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.TrimpContent(
    state: TrainingResultState,
    viewModel: TrainingResultViewModel
) {
    Column() {
        Spacer(Modifier.size(20.dp))
        TopBarTitle(
            text = stringResource(Res.string.training),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            showCurrentTime = true
        )
        Text(
            text = "${stringResource(Res.string.duration)}: ${
                state.filterSportmans.maxOf(default = 0) { it.timeTrainingSeconds }.orEmpty()
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
        MaxiOutlinedTextField(
            value = state.search,
            onValueChange = {
                viewModel.changeSearch(it)
                viewModel.search(it)
            },
            placeholder = stringResource(Res.string.search),
            modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .height(Constants.TextFieldHeight)
                .fillMaxWidth(),
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
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            Column(Modifier.weight(1f).fillMaxHeight()) {
                TitleResultBox(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    color = MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.1f),
                    text = stringResource(Res.string.trimp)
                )
                Box(
                    Modifier.weight(1f).fillMaxWidth().background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFE72163),
                                Color(0xFF3092F7)
                            )
                        )
                    )
                ) {
                    Row(
                        Modifier.fillMaxWidth().fillMaxHeight()
                            .background(color = Color.White.copy(alpha = 0.6f)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(50, 100, 150, 200, 250, 300, 350).forEach {
                            Text(
                                text = it.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaxiPulsTheme.typography.medium.copy(
                                    fontSize = 14.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            TitleResultBox(
                modifier = Modifier.width(150.dp).fillMaxHeight(),
                text = stringResource(Res.string.equal),
                maxLines = 1
            )

        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        LazyColumn(Modifier.weight(1f).fillMaxWidth()) {
            items(state.filterSportmans) {
                CellItem(
                    sportsmanTrainingResultUI = it,
                    maxTrimp = 350.0
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
            }
        }
    }
}

@Composable
private fun CellItem(sportsmanTrainingResultUI: SportsmanTrainingResultUI, maxTrimp: Double) {
    Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
        RegularResultBox(
            modifier = Modifier.width(150.dp).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanTrainingResultUI.fio,
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            color = Color.Transparent,
            alignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 20.dp)
                    .fillMaxWidth(sportsmanTrainingResultUI.trimp.toFloat() / maxTrimp.toFloat())
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.red500.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .height(20.dp).clip(RoundedCornerShape(15.dp)),
            )
        }
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.width(150.dp).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanTrainingResultUI.trimp.toString(),
            maxLines = 1
        )
    }
}
