package org.example.project.screens.tablet.log.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.trash
import org.example.project.domain.model.log.LogUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    logUI: LogUI,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {

    val screenSize = ScreenSize.currentOrThrow
    val division = when (screenSize.widthSizeClass) {
        WindowWidthSizeClass.Medium -> 1.3
        WindowWidthSizeClass.Expanded -> 1.0
        WindowWidthSizeClass.Compact -> 1.7
        else -> 1.5
    }
    val spacerDivision = division

    Column(
        modifier.background(
            color = MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
        }.animateContentSize()
    ) {
        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = (20 / division).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${logUI.dateText} ${logUI.timeText}",
                    style = MaxiPulsTheme.typography.regular.copy(
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                        fontSize = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, false),
                    maxLines = 1

                )

                VerticalDivider(
                    modifier = Modifier.padding(horizontal = 10.dp).height(17.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Text(
                    text = logUI.event.type.title,
                    style = MaxiPulsTheme.typography.bold.copy(
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                        fontSize = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f, false)
                )

            }

            Text(
                text = logUI.durationText,
                style = MaxiPulsTheme.typography.regular.copy(
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
        }

        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))


        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = (20 / division).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = logUI.event.title,
                style = MaxiPulsTheme.typography.regular.copy(
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )

            VerticalDivider(
                modifier = Modifier.padding(horizontal = 10.dp).height(17.dp),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            Text(
                text = logUI.sportsmanUI.fio,
                style = MaxiPulsTheme.typography.bold.copy(
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    fontSize = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                maxLines = 1

            )
            Icon(
                painter = painterResource(Res.drawable.trash),
                modifier = Modifier.size(24.dp).clickableBlank {
                    onDelete()
                },
                tint = MaxiPulsTheme.colors.uiKit.primary,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))

    }

}