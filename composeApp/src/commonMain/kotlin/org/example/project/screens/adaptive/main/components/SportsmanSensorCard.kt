package org.example.project.screens.adaptive.main.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.sensor_not_found
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.example.project.theme.uiKit.MaxiRoundCheckBox


@Composable
fun SportsmanSensorCard(
    modifier: Modifier = Modifier,
    number: String,
    name: String,
    lastname: String,
    middleName: String,
    compositionText: String,
    sensorId: String,
    sensorName: String,
    isSelect: Boolean,
    isBorder: Boolean,
    clickSensor: () -> Unit,
    changeSelect: () -> Unit,
) {
    val screenSize = ScreenSize.currentOrThrow
    val division = when (screenSize.widthSizeClass) {
        WindowWidthSizeClass.Medium -> 1.5
        WindowWidthSizeClass.Expanded -> 1.0
        WindowWidthSizeClass.Compact -> 2.0
        else -> 1.5
    }
    val spacerDivision = division

    Column(
        modifier.background(
            color = MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).then(
            if (isBorder) Modifier.border(
                width = 1.dp,
                color = MaxiPulsTheme.colors.uiKit.green500.copy(alpha = 0.5f),
                shape = RoundedCornerShape(25.dp)
            ) else Modifier
        ).clickableBlank() {
            changeSelect()
        }.animateContentSize()
    ) {
        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.background(
                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                        shape = CircleShape
                    ).clip(CircleShape).size((60.0).dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.profile),
                        modifier = Modifier.size(width = (25).dp, (32).dp),
                        colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size((20 / spacerDivision).dp))

                Column(modifier = Modifier) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = number, style = MaxiPulsTheme.typography.bold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp,
                                lineHeight = 14.sp

                            )
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "$lastname ${name.firstOrNull() ?: ""}.${middleName.firstOrNull() ?: ""}",
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Spacer(modifier = Modifier.size(7.dp))
                    Text(
                        text = compositionText,
                        style = MaxiPulsTheme.typography.medium.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 14.sp,
                            lineHeight = 14.sp

                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
            MaxiRoundCheckBox(
                size = 30f,
                isChecked = isSelect,
                onValueChange = {
                    changeSelect()
                })
        }
        Spacer(Modifier.size(20.dp))
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).background(
                color = if (sensorId.isBlank()) MaxiPulsTheme.colors.uiKit.grey500 else MaxiPulsTheme.colors.uiKit.primary.copy(
                    alpha = 0.15f
                ),
                shape = RoundedCornerShape(15.dp)
            ).clip(RoundedCornerShape(15.dp)).clickableBlank() {
                clickSensor()
            }
        ) {
            Text(
                text = if (sensorId.isBlank()) stringResource(Res.string.sensor_not_found) else "$sensorName $sensorId",
                style = MaxiPulsTheme.typography.regular.copy(
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                color = MaxiPulsTheme.colors.uiKit.textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 15.dp)
            )
        }
        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))
    }

}
