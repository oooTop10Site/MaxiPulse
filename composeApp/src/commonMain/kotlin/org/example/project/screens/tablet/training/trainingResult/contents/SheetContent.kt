package org.example.project.screens.tablet.training.trainingResult.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.avg
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.duration
import maxipuls.composeapp.generated.resources.fio
import maxipuls.composeapp.generated.resources.kcal
import maxipuls.composeapp.generated.resources.max
import maxipuls.composeapp.generated.resources.min
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.time
import maxipuls.composeapp.generated.resources.time_in_zone
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.trimp
import maxipuls.composeapp.generated.resources.zone_number
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
internal fun ColumnScope.SheetContent(
    search: String,
    filterSportmans: List<SportsmanTrainingResultUI>, viewModel: TrainingResultViewModel
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
                filterSportmans.maxOf(default = 0) { it.timeTrainingSeconds }.orEmpty()
                    .secondsToUI()
            }", style = MaxiPulsTheme.typography.regular.copy(
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
            value = search,
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

        Row(modifier = Modifier.fillMaxWidth().height(110.dp)) {
            TitleResultBox(
                modifier = Modifier.weight(0.13f).fillMaxHeight(),
                text = stringResource(Res.string.fio)
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            TitleResultBox(
                modifier = Modifier.weight(0.1f).fillMaxHeight(),
                text = stringResource(Res.string.time)
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            Column(Modifier.weight(0.34f)) {
                TitleResultBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    text = stringResource(Res.string.time_in_zone),
                    maxLines = 2
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val colors = listOf(
                        Color(0xFFD6D8F1), // Голубая
                        Color(0xFF9CACDF), // Синяя
                        Color(0xFF9ED759), // Зеленая
                        Color(0xFFFFBF6B), // Оранжевая
                        Color(0xFFE74870)  // Красная
                    )
                    colors.forEachIndexed { index, item ->
                        TitleResultBox(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            text = stringResource(
                                Res.string.zone_number,
                                index + 1
                            ),
                            color = item
                        )
                        if (index != colors.lastIndex) {
                            VerticalDivider(
                                modifier = Modifier.fillMaxHeight(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                        }
                    }
                }
            }
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            Column(Modifier.weight(0.2f)) {
                TitleResultBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    text = stringResource(Res.string.chss)
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleResultBox(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        text = stringResource(Res.string.avg)
                    )
                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )

                    TitleResultBox(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        text = stringResource(Res.string.min)
                    )
                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )

                    TitleResultBox(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        text = stringResource(Res.string.max)
                    )
                }
            }
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            TitleResultBox(
                modifier = Modifier.weight(0.1f).fillMaxHeight(),
                text = stringResource(Res.string.trimp)
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            TitleResultBox(
                modifier = Modifier.weight(0.1f).fillMaxHeight(),
                text = stringResource(Res.string.kcal)
            )
        }
    }
    HorizontalDivider(
        Modifier.fillMaxWidth(),
        color = MaxiPulsTheme.colors.uiKit.divider
    )
    LazyColumn(modifier = Modifier.weight(1f)) {
        items(filterSportmans) {
            CellItem(sportsmanTrainingResultUI = it)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
        }
    }
}

@Composable
private fun CellItem(sportsmanTrainingResultUI: SportsmanTrainingResultUI) {
    Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
        RegularResultBox(
            modifier = Modifier.weight(0.13f).fillMaxHeight(),
            color = Color.Transparent,
            text = sportsmanTrainingResultUI.fio,
            maxLines = 2
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(0.1f).fillMaxHeight(),
            text = sportsmanTrainingResultUI.time.secondsToUI(),
            color = Color.Transparent,
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.34f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val colors = listOf(
                sportsmanTrainingResultUI.zone1,
                sportsmanTrainingResultUI.zone2,
                sportsmanTrainingResultUI.zone3,
                sportsmanTrainingResultUI.zone4,
                sportsmanTrainingResultUI.zone5,
            )
            colors.forEachIndexed { index, item ->
                println("item - $item")
                RegularResultBox(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    text = item.secondsToUI(),
                    color = Color.Transparent
                )
                if (index != colors.lastIndex) {
                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
            }
        }
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                text = sportsmanTrainingResultUI.heartRateAvg.toString(),
                color = Color.Transparent,
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                text = sportsmanTrainingResultUI.heartRateMin.toString(),
                color = Color.Transparent,
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                text = sportsmanTrainingResultUI.heartRateMax.toString(),
                color = Color.Transparent,
            )
        }
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        RegularResultBox(
            modifier = Modifier.weight(0.1f).fillMaxHeight(),
            text = sportsmanTrainingResultUI.trimp.toString(),
            color = Color.Transparent,

            )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )

        RegularResultBox(
            modifier = Modifier.weight(0.1f).fillMaxHeight(),
            text = sportsmanTrainingResultUI.kcal.toString(),
            color = Color.Transparent,
        )
    }
}