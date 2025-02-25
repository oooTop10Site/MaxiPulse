package org.example.project.theme.uiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.MaxiPulsTheme


@Composable
fun HeartBPMGraph(modifier: Modifier = Modifier) {
    val chssData = listOf(
        ChssSegment("ЧСС max", 206, listOf(Color(0xFF6C001B), Color(0xFFDF0B40))),
        ChssSegment("96%", 198, listOf(Color(0xFFDF0B40), Color(0xFFFFA93A))),
        ChssSegment("91%", 187, listOf(Color(0xFFFFA93A), Color(0xFF96D34B))),
        ChssSegment("80%", 165, listOf(Color(0xFF96D34B), Color(0xFF3B6ECF))),
        ChssSegment("70%", 144, listOf(Color(0xFF3B6ECF), Color(0xFFAEC6F3))),
        ChssSegment("ЧСС покоя", 0, listOf(Color(0xFFAEC6F3), Color(0xFF364562)))
    ).sortedBy { it.value }.reversed()
    val segmentsColor = listOf<Color>(
        Color(0xFFDF0B40),
        Color(0xFFFFA93A),
        Color(0xFF96D34B),
        Color(0xFF3B6ECF),
        Color(0xFFAEC6F3)
    )
    BoxWithConstraints(
        modifier = modifier.clip(RoundedCornerShape(25.dp)),
    ) {
        val height = maxHeight
        val chartHeight = height / 5
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            segmentsColor.forEach {
                Box(modifier = Modifier.fillMaxWidth().weight(1f).background(color = it))
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            chssData.firstOrNull()?.let {
                ChssSegmentItem(modifier = Modifier, it)
            }
            chssData.lastOrNull()?.let {
                ChssSegmentItem(modifier = Modifier, it)
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.height(chartHeight - 25.dp)
            ) {

            }

            chssData.drop(1).dropLast(1).forEachIndexed { index, segment ->
                println("index - $index")


                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        ChssSegmentItem(modifier = Modifier, segment)
                    }
                    if (index != 3) {
                        Box(modifier = Modifier.weight(1f, false)) {
                            Box(modifier = Modifier.fillMaxHeight(1f - (index.toFloat() * 0.35f)))
                        }
                    }
                }


            }
//            Spacer(Modifier.size(5.dp * 4))
            Box(
                modifier = Modifier.height(chartHeight - 25.dp)
            ) {

            }

        }
    }
}

@Composable
fun ChssSegmentItem(modifier: Modifier = Modifier, segment: ChssSegment) {
    Box(
        modifier = Modifier.background(color = MaxiPulsTheme.colors.uiKit.white, shape = RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(25.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.verticalGradient(colors = segment.backgroundColor.map { it.copy(alpha = 0.75f) }),
                    shape = RoundedCornerShape(25.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = segment.percentage,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Text(
                    text = segment.value.toString(),
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }
}

data class ChssSegment(
    val percentage: String,
    val value: Int,
    val backgroundColor: List<Color>
)

