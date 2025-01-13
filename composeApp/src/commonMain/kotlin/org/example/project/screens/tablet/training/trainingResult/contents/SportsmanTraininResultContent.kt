package org.example.project.screens.tablet.training.trainingResult.contents

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.chss_avg
import maxipuls.composeapp.generated.resources.chss_min
import maxipuls.composeapp.generated.resources.chss_peak
import maxipuls.composeapp.generated.resources.duration
import maxipuls.composeapp.generated.resources.kcal
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.trimp
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.ext.roundToIntOrNull
import org.example.project.ext.secondsToUI
import org.example.project.ext.toTimeUI
import org.example.project.screens.tablet.training.trainingResult.TrainingResultState
import org.example.project.screens.tablet.training.trainingResult.TrainingResultViewModel
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.HeartRateGraph
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
internal fun SportsmanTraininResultContent(
    state: TrainingResultState,
    viewModel: TrainingResultViewModel,
    sportsmanTrainingResultUI: SportsmanTrainingResultUI
) {
    Column() {
        Spacer(Modifier.size(20.dp))
        TopBarTitle(
            text = stringResource(Res.string.training),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            showCurrentTime = true
        )
        Text(
            text = "${stringResource(Res.string.duration)}: ${sportsmanTrainingResultUI.timeTrainingSeconds.secondsToUI()}",
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
        Spacer(Modifier.size(40.dp))
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier.size(100.dp),
                ) {
                    if (sportsmanTrainingResultUI.avatar.isBlank()) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                shape = CircleShape
                            ).clip(CircleShape).align(Alignment.Center)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.profile),
                                modifier = Modifier.size(
                                    width = 44.dp,
                                    60.dp
                                ).align(Alignment.Center),
                                colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                contentDescription = null
                            )
                        }
                    } else {
                        MaxiImage(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                .clip(CircleShape),
                            url = sportsmanTrainingResultUI.avatar,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier.background(
                            MaxiPulsTheme.colors.uiKit.grey800,
                            shape = CircleShape
                        ).clip(CircleShape).size(33.dp).align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sportsmanTrainingResultUI.number.toString(),
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                fontSize = 16.sp,
                                lineHeight = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.size(25.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = sportsmanTrainingResultUI.fio,
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "${stringResource(Res.string.age)}: ${
                            stringResource(
                                Res.string.age_text,
                                sportsmanTrainingResultUI.age
                            )
                        }",
                        style = MaxiPulsTheme.typography.regular.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "${stringResource(Res.string.chss_peak)}: ${
                            sportsmanTrainingResultUI.heartRateMax
                        }",
                        style = MaxiPulsTheme.typography.regular.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val sumOfDuration =
                    sportsmanTrainingResultUI.zone1 + sportsmanTrainingResultUI.zone2 + sportsmanTrainingResultUI.zone3 + sportsmanTrainingResultUI.zone4 + sportsmanTrainingResultUI.zone5
                val zoneColors = listOf(
                    Color(0xFFDF0B40),  // Красная
                    Color(0xFFFFA93A), // Оранжевая
                    Color(0xFF96D34B), // Зеленая
                    Color(0xFF3B6ECF), // Синяя
                    Color(0xFFAEC6F3), // Голубая
                )
                zoneColors.forEachIndexed { index, item ->
                    val duration = when (index + 1) {
                        1 -> sportsmanTrainingResultUI.zone5
                        2 -> sportsmanTrainingResultUI.zone4
                        3 -> sportsmanTrainingResultUI.zone3
                        4 -> sportsmanTrainingResultUI.zone2
                        5 -> sportsmanTrainingResultUI.zone1
                        else -> sportsmanTrainingResultUI.zone1
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().height(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = duration.secondsToUI(),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.width(65.dp)
                        )
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight()
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    color = MaxiPulsTheme.colors.uiKit.white500,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            val division = duration.toFloat() / sumOfDuration.toFloat()
                            Box(
                                modifier = Modifier.fillMaxWidth(division)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(color = item, shape = RoundedCornerShape(20.dp))
                                    .align(Alignment.CenterStart)
                            )
                            Text(
                                text = "${(division * 100).roundToIntOrNull().orEmpty()}%",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp,
                                    color = if (division >= 0.6f) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.size(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 72.dp).background(
                color = MaxiPulsTheme.colors.uiKit.card,
                shape = RoundedCornerShape(100.dp)
            ).height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "${stringResource(Res.string.chss_peak)}: ${sportsmanTrainingResultUI.heartRateMax}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Text(
                text = "${stringResource(Res.string.chss_min)}: ${sportsmanTrainingResultUI.heartRateMin}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Text(
                text = "${stringResource(Res.string.chss_avg)}: ${sportsmanTrainingResultUI.heartRateAvg}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Text(
                text = "${stringResource(Res.string.trimp)}: ${sportsmanTrainingResultUI.trimp}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )
            Text(
                text = "${stringResource(Res.string.kcal)}: ${sportsmanTrainingResultUI.kcal}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                )
            )

        }
        Spacer(Modifier.size(60.dp))

        Text(
            text = stringResource(Res.string.chss),
            style = MaxiPulsTheme.typography.bold.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(20.dp))

        HeartRateGraph(
            modifier = Modifier.weight(1f).padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            heartRateData = sportsmanTrainingResultUI.heartRate,
            showTime = true
        )
    }
}