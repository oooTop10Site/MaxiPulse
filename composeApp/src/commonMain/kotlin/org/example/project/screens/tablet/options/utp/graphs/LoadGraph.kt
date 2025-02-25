package org.example.project.screens.tablet.options.utp.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeLoadBackgroundColor
import org.example.project.theme.MaxiPulsTheme
import kotlin.math.abs

@Composable
fun LoadGraph(modifier: Modifier = Modifier) {
    val maxValue = 1400f
    val trainingUIData = listOf(
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 560f)),
        LoadGraphUI(existData = Pair(600f, 900f), expectData = Pair(570f, 1290f)),
        LoadGraphUI(existData = Pair(470f, 1010f), expectData = Pair(470f, 1200f)),
        LoadGraphUI(existData = Pair(410f, 1130f), expectData = Pair(340f, 490f)),
        LoadGraphUI(existData = Pair(300f, 1000f), expectData = Pair(210f, 750f)),
        LoadGraphUI(existData = Pair(234f, 940f), expectData = Pair(10f, 970f)),
        LoadGraphUI(existData = Pair(671f, 840f), expectData = Pair(780f, 1100f))
    )
    // Define zones similar to HeartRateGraph
    val zones = listOf(
        1100f to 1400f,   // Fourth zone
        1050f to 1100f,  // Third zone
        700f to 1050f,   // Second zone
        0f to 700f,      // First zone

    )

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            zones.forEachIndexed { index, (start, end) ->
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier.width(72.dp).fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = end.toString(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 14.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                textAlign = TextAlign.Start
                            ),
                            modifier = Modifier.width(72.dp),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f) // Equal height for each zone
                            .fillMaxHeight()
                            .background(
                                color = end.toAnalizeLoadBackgroundColor(),
                                shape = when {
                                    index == zones.size - 1 -> RoundedCornerShape(bottomStart = 25.dp)
                                    index == 0 -> RoundedCornerShape(topStart = 25.dp)
                                    else -> RoundedCornerShape(0.dp)
                                }
                            )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 112.dp, end = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            trainingUIData.forEach { data ->
                Box(modifier = Modifier.width(50.dp)) {
                    // Expected data column
                    // Normalize expected data within zones
                    Column(
                        Modifier.width(35.dp).align(Alignment.CenterEnd).fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val (startValue, endValue) = data.expectData
                        val normalizedHeightBottom =
                            normalizeValueInZones(0f, startValue, zones)
                        val normalizedHeightMiddle =
                            normalizeValueInZones(startValue, endValue, zones)
                        val normalizedHeightTop =
                            normalizeValueInZones(endValue, maxValue, zones)
                        Column(modifier = Modifier.weight(normalizedHeightTop)) {
                            Spacer(Modifier.weight(1f))
                        }

                        Column(
                            modifier = Modifier.weight(normalizedHeightMiddle)
                                .width(35.dp).background(
                                    color = MaxiPulsTheme.colors.uiKit.black50,
                                    shape = RoundedCornerShape(100.dp)
                                )
                        ) {
                            Spacer(Modifier.weight(1f))
                        }
                        Column(
                            modifier = Modifier.weight(normalizedHeightBottom),
                        ) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Column(
                        Modifier
                            .width(35.dp)
                            .align(Alignment.CenterStart)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val (startValue, endValue) = data.existData
                        val normalizedHeightBottom =
                            normalizeValueInZones(0f, startValue, zones)
                        val normalizedHeightMiddle =
                            normalizeValueInZones(startValue, endValue, zones)
                        val normalizedHeightTop =
                            normalizeValueInZones(endValue, maxValue, zones)
                        Column(modifier = Modifier.weight(normalizedHeightTop)) {
                            Spacer(Modifier.weight(1f))
                        }

                        Column(
                            modifier = Modifier.weight(normalizedHeightMiddle)
                                .width(35.dp).background(
                                    color = MaxiPulsTheme.colors.uiKit.white,
                                    shape = RoundedCornerShape(100.dp)
                                )
                        ) {
                            Spacer(Modifier.weight(1f))
                        }
                        Column(
                            modifier = Modifier.weight(normalizedHeightBottom),
                        ) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

// Helper function to normalize values within zones
private fun normalizeValueInZones(
    startValue: Float,
    endValue: Float,
    zones: List<Pair<Float, Float>>
): Float {
    println("startValue - $startValue")
    println("endValue - $endValue")
    var normalizedStart = 0f
    var normalizedEnd = 0f

    zones.sortedBy { it.first }.forEachIndexed { index, (zoneStart, zoneEnd) ->
        val zoneRange = zoneEnd - zoneStart
        val zoneFraction = 1f / zones.size // Equal fraction for each zone

        // Calculate normalized position for start value
        if (startValue >= zoneStart && startValue <= zoneEnd) {
            val valueInZone = startValue - zoneStart
            normalizedStart = (index * zoneFraction) + (valueInZone / zoneRange * zoneFraction)
        }

        // Calculate normalized position for end value
        if (endValue >= zoneStart && endValue <= zoneEnd) {
            val valueInZone = endValue - zoneStart
            normalizedEnd = (index * zoneFraction) + (valueInZone / zoneRange * zoneFraction)
        }
    }

    // Return the normalized height difference
    return normalizedEnd - normalizedStart
}


data class LoadGraphUI(val existData: Pair<Float, Float>, val expectData: Pair<Float, Float>)