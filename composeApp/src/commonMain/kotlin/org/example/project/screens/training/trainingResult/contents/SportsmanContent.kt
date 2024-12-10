package org.example.project.screens.training.trainingResult.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.chss_peak
import maxipuls.composeapp.generated.resources.female_ic
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.sensor
import maxipuls.composeapp.generated.resources.sensor_ic
import maxipuls.composeapp.generated.resources.sportsman_ic
import maxipuls.composeapp.generated.resources.training
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.screens.training.trainingResult.TrainingResultState
import org.example.project.screens.training.trainingResult.TrainingResultViewModel
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.TopBarTitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SportsmanContent(
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
            text = "Продолжительность: 00:34:02",
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
            Row(modifier = Modifier.weight(1f)) {

            }
        }
    }
}