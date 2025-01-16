package org.example.project.screens.tablet.tests.shuttleRun.result.contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.chss_pano
import maxipuls.composeapp.generated.resources.chss_peak
import maxipuls.composeapp.generated.resources.distance
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.performance
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.recovery
import maxipuls.composeapp.generated.resources.reputation
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.time
import org.example.project.domain.model.sportsman.SportsmanGraph
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.ext.secondsToUI
import org.example.project.screens.tablet.tests.shuttleRun.result.ShuttleRunResultState
import org.example.project.screens.tablet.tests.shuttleRun.result.ShuttleRunResultViewModel
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.TopBarTitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.datetime.Month
import maxipuls.composeapp.generated.resources.duration
import org.example.project.ext.maxOf
import org.example.project.screens.tablet.tests.shuttleRun.result.SelectableTab
import org.example.project.theme.uiKit.BarChart
import org.example.project.utils.orEmpty

@Composable
internal fun ColumnScope.ShuttleRunSportsmanContent(
    modifier: Modifier = Modifier,
    sportsman: SportsmanShuttleRunResultUI,
    state: ShuttleRunResultState,
    viewModel: ShuttleRunResultViewModel
) {
    var selectSportsmanGraph by remember { mutableStateOf(SportsmanGraph.MPK) }
    Column(modifier = modifier) {
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
        Spacer(Modifier.size(30.dp))
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).background(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(25.dp)
            ).clip(RoundedCornerShape(25.dp)), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(100.dp)) {
                        Box(
                            modifier = Modifier.background(
                                MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                shape = CircleShape
                            ).clip(CircleShape).size(100.0.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (sportsman.avatar.isBlank()) {
                                Image(
                                    painter = painterResource(Res.drawable.profile),
                                    modifier = Modifier.size(
                                        width = 46.dp,
                                        height = 60.dp
                                    ),
                                    colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                    contentDescription = null
                                )
                            } else {
                                MaxiImage(
                                    modifier = Modifier.fillMaxSize(),
                                    url = sportsman.avatar,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Box(
                            modifier = Modifier.background(
                                MaxiPulsTheme.colors.uiKit.grey800,
                                shape = CircleShape
                            ).clip(CircleShape).size(33.dp).align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = sportsman.number.toString(),
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
                    Spacer(modifier = Modifier.size((30).dp))

                    Column(
                        modifier = Modifier,
                    ) {
                        Text(
                            text = sportsman.fio,
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.size((10).dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(Res.string.age_text, sportsman.age),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, false)
                            )

                            Spacer(modifier = Modifier.size(15.dp))
                            VerticalDivider(
                                modifier = Modifier.height(20.dp),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                            Spacer(modifier = Modifier.size(15.dp))

                            Text(
                                text = "${stringResource(Res.string.chss_peak)}: ${sportsman.heartRateMax}",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )

                        }
                    }
                }
                Spacer(Modifier.size(30.dp))
                VerticalDivider(Modifier.height(100.dp), color = MaxiPulsTheme.colors.uiKit.divider)
                Spacer(Modifier.size(30.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                    TextItem(
                        Modifier.width(150.dp),
                        text = "${stringResource(Res.string.chss_pano)}:",
                        value = sportsman.chssPano.toString()
                    )

                    Spacer(Modifier.size(10.dp))

                    TextItem(
                        Modifier.width(150.dp),
                        text = "${stringResource(Res.string.mpk)}:",
                        value = sportsman.mpk.toString()
                    )
                }
            }
        }
        Spacer(Modifier.size(20.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).background(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(25.dp)
            ).clip(RoundedCornerShape(25.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextItem(
                            Modifier.width(150.dp),
                            text = stringResource(Res.string.distance),
                            value = sportsman.distance.toString()
                        )

                        Spacer(Modifier.size(10.dp))

                        TextItem(
                            Modifier.width(150.dp),
                            text = stringResource(Res.string.time),
                            value = sportsman.time.secondsToUI()
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier.height(50.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextItem(
                            Modifier.width(150.dp),
                            text = stringResource(Res.string.mpk),
                            value = sportsman.mpk.toString()
                        )

                        Spacer(Modifier.size(10.dp))

                        TextItem(
                            Modifier.width(150.dp),
                            text = stringResource(Res.string.chss_max),
                            value = sportsman.heartRateMax.toString()
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier.height(50.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextItem(
                            Modifier.width(150.dp),
                            text = stringResource(Res.string.chss_pano),
                            value = sportsman.chssPano.toString()
                        )

                        Spacer(Modifier.size(10.dp))

                        TextItem(
                            Modifier.width(150.dp),
                            text = "",
                            value = ""
                        )
                    }
                }
                Spacer(Modifier.size(25.dp))
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    contentPadding = PaddingValues(horizontal = 50.dp)
                ) {
                    item() {
                        RewardItem(modifier, text = stringResource(Res.string.recovery))
                    }
                    item {
                        RewardItem(modifier, text = stringResource(Res.string.performance))
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
            LazyColumn(
                modifier = Modifier.width(190.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(top = 17.dp, bottom = 41.dp, start = 20.dp)
            ) {
                items(SportsmanGraph.entries) {
                    SelectableTab(
                        modifier = Modifier.height(37.dp),
                        text = stringResource(it.text),
                        isSelect = it == selectSportsmanGraph,
                    ) {
                        selectSportsmanGraph = it
                    }
                }
            }
            Spacer(Modifier.size(60.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(selectSportsmanGraph) {
                   SportsmanGraph.MPK -> {
                       Text(
                           text = stringResource(Res.string.mpk),
                           style = MaxiPulsTheme.typography.bold.copy(
                               fontSize = 16.sp,
                               lineHeight = 16.sp,
                               color = MaxiPulsTheme.colors.uiKit.textColor
                           ),
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.padding(vertical = 10.dp)
                       )
                       BarChart(
                           modifier = Modifier.fillMaxWidth().weight(1f), listOf(
                               Month.JANUARY to 50,
                               Month.FEBRUARY to 60,
                               Month.MARCH to 70,
                               Month.APRIL to 60,
                               Month.MAY to 50,
                               Month.JUNE to 100 // Максимум, будет подсвечен
                           )
                       )
                   }
                   else -> {}
                }
                Spacer(Modifier.size(30.dp))

            }
            Spacer(Modifier.size(60.dp))
        }
    }
}

@Composable
internal fun RewardItem(modifier: Modifier, text: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaxiPulsTheme.typography.regular.copy(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.size(25.dp))

        Box(
            modifier = Modifier.background(
                color = Color(0xFFEF85A0),
                shape = RoundedCornerShape(25.dp)
            ).width(80.dp).height(50.dp).clip(RoundedCornerShape(25.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.reputation),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
            )
        }


    }
}

@Composable
private fun TextItem(modifier: Modifier, text: String, value: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaxiPulsTheme.typography.regular.copy(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaxiPulsTheme.typography.regular.copy(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}