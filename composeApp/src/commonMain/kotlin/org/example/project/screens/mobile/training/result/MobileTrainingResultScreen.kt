package org.example.project.screens.mobile.training.result

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.assigned_training
import maxipuls.composeapp.generated.resources.chss_avg
import maxipuls.composeapp.generated.resources.chss_min
import maxipuls.composeapp.generated.resources.chss_minimum
import maxipuls.composeapp.generated.resources.chss_peak
import maxipuls.composeapp.generated.resources.duration
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.results
import maxipuls.composeapp.generated.resources.time_in_zones
import maxipuls.composeapp.generated.resources.training_is_end
import maxipuls.composeapp.generated.resources.trimp
import maxipuls.composeapp.generated.resources.zone_number
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.training.TrainingUI
import org.example.project.ext.max
import org.example.project.ext.secondsToUI
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.mobile.borgScale.MobileBackIcon
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.SportsmanMeasure
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class MobileTrainingResultScreen(private val trainingUI: TrainingUI) : Screen {

    @Composable
    override fun Content() {
        val navigator = RootNavigator.currentOrThrow
        MaxiPageContainerMobile(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarMobile(
                modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
                title = stringResource(Res.string.assigned_training),
                leadingIcon = {
                    MobileBackIcon(modifier = Modifier.size(40.dp)) {
                        navigator.pop()
                    }
                }
            )
        }) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Text(
                    text = stringResource(Res.string.training_is_end),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                    modifier = Modifier
                )

                Spacer(Modifier.size(20.dp))

                Text(
                    text = stringResource(Res.string.results),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                    modifier = Modifier
                )
                Spacer(Modifier.size(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(
                        15.dp
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 7.dp,
                        hoveredElevation = 2.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaxiPulsTheme.colors.uiKit.white)
                ) {
                    println("result heart rate - ${trainingUI.sensorUI.heartRate}")
                    ResultItem(
                        title = Res.string.duration,
                        value = trainingUI.duration.secondsToUI()
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    )
                    ResultItem(
                        title = Res.string.chss_peak,
                        value = trainingUI.sensorUI.heartRate.max().toString()
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    )
                    ResultItem(
                        title = Res.string.chss_avg,
                        value = trainingUI.sensorUI.heartRate.average().toString()
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    )
                    ResultItem(
                        title = Res.string.chss_minimum,
                        value = trainingUI.sensorUI.heartRate.min().toString()
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    )
                    ResultItem(
                        title = Res.string.trimp,
                        value = SportsmanMeasure.trimp(
                            isMale = true,
                            trainingTimeSeconds = trainingUI.duration,
                            chssReasting = 50,
                            chssMaxOnTraining = trainingUI.sensorUI.heartRate.max(default = 0),
                            chssMax = 220
                        ).toString()
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(Res.string.time_in_zones),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                    modifier = Modifier
                )
                Spacer(Modifier.size(10.dp))
                val zonesDuration = listOf<Pair<Color, Int>>(
                    Pair(Color(0xFFD6D8F1).copy(alpha = 0.5f), 15),
                    Pair(Color(0xFF9CACDF).copy(alpha = 0.5f), 35),
                    Pair(Color(0xFF9ED759).copy(alpha = 0.5f), 22),
                    Pair(Color(0xFFFFBF6B).copy(alpha = 0.5f), 11),
                    Pair(Color(0xFFE74870).copy(alpha = 0.5f), 141)
                )
                zonesDuration.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.height(37.dp).clip(RoundedCornerShape(100.dp))
                            .fillMaxWidth()
                            .background(color = item.first, shape = RoundedCornerShape(100.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.zone_number, index + 1),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                            ),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Text(
                            text = item.second.secondsToUI(),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                            ),
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                    Spacer(Modifier.size(10.dp))
                }
                Spacer(Modifier.weight(1f))
                MaxiButton(
                    modifier = Modifier.padding(bottom = 20.dp).height(40.dp).fillMaxWidth(),
                    buttonTextStyle = ButtonTextStyle.MobileBold,
                    text = stringResource(Res.string.end),
                    onClick = {
                        navigator.pop()
                    })
            }

        }
    }

    @Composable
    fun ResultItem(title: StringResource, value: String) {
        Column() {
            Spacer(Modifier.size(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(title),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                )

                Text(
                    text = value,
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )

            }
            Spacer(Modifier.size(20.dp))
        }
    }
}