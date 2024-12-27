package org.example.project.screens.tablet.sportsman.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.female_ic
import maxipuls.composeapp.generated.resources.height_text
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.sportsman_ic
import maxipuls.composeapp.generated.resources.weight_text
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun SportsmanCard(
    modifier: Modifier = Modifier,
    number: Int,
    name: String,
    lastname: String,
    middleName: String,
    age: Int,
    height: Int,
    weight: Int,
    avatar: String,
    isMale: Boolean,
    showEditIcon: Boolean = true,
    editIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    onEdit: () -> Unit,
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
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
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
                    ).clip(CircleShape).size((100.0 / division).dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatar.isBlank()) {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            modifier = Modifier.size(
                                width = (42 / division).dp,
                                (54 / division).dp
                            ),
                            colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                            contentDescription = null
                        )
                    } else {
                        MaxiImage(
                            modifier = Modifier.fillMaxSize(),
                            url = avatar,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.size((20 / spacerDivision).dp))

                Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = number.toString(), style = MaxiPulsTheme.typography.bold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        VerticalDivider(
                            modifier = Modifier.height(17.dp),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        Text(
                            text = "$lastname $name $middleName",
                            style = MaxiPulsTheme.typography.bold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Spacer(modifier = Modifier.size((17 / spacerDivision).dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(if (isMale) Res.drawable.sportsman_ic else Res.drawable.female_ic),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaxiPulsTheme.colors.uiKit.textColor
                        )

                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        VerticalDivider(
                            modifier = Modifier.height(17.dp),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))

                        Text(
                            text = stringResource(Res.string.age_text, age),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, false)
                        )

                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        VerticalDivider(
                            modifier = Modifier.height(17.dp),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))

                        Text(
                            text = stringResource(Res.string.height_text, height),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, false)
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        VerticalDivider(
                            modifier = Modifier.height(17.dp),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))

                        Text(
                            text = stringResource(Res.string.weight_text, weight),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 14.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, false)
                        )

                    }
                }
            }
            if (showEditIcon) {
                editIcon?.invoke() ?: @Composable {
                    Icon(
                        painterResource(Res.drawable.pencil),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickableBlank {
                            onEdit()
                        },
                        tint = MaxiPulsTheme.colors.uiKit.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))

    }

}