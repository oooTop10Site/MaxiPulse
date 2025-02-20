package org.example.project.screens.tablet.options.utp.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeLoadBackgroundColor
import org.example.project.ext.toAnalizeTensionBackgroundColor
import org.example.project.screens.tablet.loadAnalize.TensionUI
import org.example.project.theme.MaxiPulsTheme

@Composable
fun LoadGraph(modifier: Modifier = Modifier) {
    val trainingUIData = listOf(
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 1200f)),
        LoadGraphUI(existData = Pair(600f, 900f), expectData = Pair(570f, 1210f)),
        LoadGraphUI(existData = Pair(470f, 1010f), expectData = Pair(470f, 1290f)),
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 1200f)),
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 1200f)),
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 1200f)),
        LoadGraphUI(existData = Pair(400f, 1100f), expectData = Pair(370f, 1200f))
    )
    val daysOfWeek = getCurrentWeekDates()
    val ratings = listOf(1400f, 1100f, 1050f, 700f)
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            ratings.forEachIndexed { index, item ->
                val shape = when {
                    index == ratings.size - 1 -> RoundedCornerShape(
                        bottomStart = 25.dp
                    )

                    index == 0 -> RoundedCornerShape(
                        topStart = 25.dp,
                    )

                    else -> RoundedCornerShape(
                        0.dp
                    )
                }
                val next = ratings.getOrNull(index + 1) ?: 0f
                Row(modifier = Modifier.weight((item - next) / 1400f)) {
                    Box(
                        modifier = Modifier.width(72.dp).fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                    }
                    Box(
                        Modifier.weight(1f).fillMaxHeight().background(
                            color = item.toAnalizeLoadBackgroundColor(),
                            shape = shape
                        ).clip(
                            shape
                        )
                    )
                }
            }
        }


    }

}

data class LoadGraphUI(val existData: Pair<Float, Float>, val expectData: Pair<Float, Float>)