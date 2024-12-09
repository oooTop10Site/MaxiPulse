package org.example.project.screens.training.trainingResult

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
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
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.female_ic
import maxipuls.composeapp.generated.resources.heart_rate_peak_player
import maxipuls.composeapp.generated.resources.height_text
import maxipuls.composeapp.generated.resources.pencil
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.share
import maxipuls.composeapp.generated.resources.sportsman_ic
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.weight_text
import maxipuls.composeapp.generated.resources.zip
import org.example.project.ext.clickableBlank
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class TrainingResultScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = RootNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            TrainingResultViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer() {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.width(330.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(22.dp)
                        ) {
                            BackIcon(modifier = Modifier.size(40.dp)) {
                                navigator.pop()
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.background(
                                        MaxiPulsTheme.colors.uiKit.grey400,
                                        shape = CircleShape
                                    ).clip(CircleShape).size(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.profile),
                                        modifier = Modifier.size(width = 22.5.dp, 30.dp),
                                        colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                        contentDescription = null
                                    )
                                }
                                Column(modifier = Modifier.padding(start = 10.dp)) {
                                    Text(
                                        text = "СШОР Гуляев Николай",
                                        style = MaxiPulsTheme.typography.semiBold.copy(
                                            fontSize = 14.sp,
                                            lineHeight = 12.sp,
                                            color = MaxiPulsTheme.colors.uiKit.textColor
                                        )
                                    )

                                    Text(
                                        text = "Вологда",
                                        style = MaxiPulsTheme.typography.semiBold.copy(
                                            fontSize = 14.sp,
                                            color = MaxiPulsTheme.colors.uiKit.textColor
                                        ),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            state.tabs.forEach {
                                SelectableTab(
                                    modifier = Modifier.height(37.dp).fillMaxWidth(),
                                    stringResource(it.text),
                                    isSelect = state.currentTab == it
                                ) {
                                    viewModel.changeTab(it)
                                }
                            }
                        }

                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.height(Constants.TextFieldHeight).weight(1f)
                                    .clip(
                                        RoundedCornerShape(100.dp)
                                    ).background(
                                        shape = RoundedCornerShape(100.dp),
                                        color = MaxiPulsTheme.colors.uiKit.grey400
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.share),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                            Spacer(Modifier.size(20.dp))

                            Box(
                                modifier = Modifier.height(Constants.TextFieldHeight).weight(1f)
                                    .clip(
                                        RoundedCornerShape(100.dp)
                                    ).background(
                                        shape = RoundedCornerShape(100.dp),
                                        color = MaxiPulsTheme.colors.uiKit.grey400
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.zip),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                    HorizontalDivider(
                        Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    LazyColumn(modifier = Modifier, contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        items(state.sportsmans) {
                            SportsmanCard(
                                modifier = Modifier.fillMaxWidth(),
                                number = it.number,
                                heartRateMax = it.heartRateMax,
                                name = it.firstname,
                                lastname = it.lastname,
                                middleName = it.middleName,
                                avatar = it.avatar,
                                age = it.age,
                            ) {

                            }
                        }
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Column(modifier = Modifier.weight(1f)) {
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
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 3.dp, start = 16.dp, end = 16.dp)
                    )

                    MaxiOutlinedTextField(
                        value = state.search,
                        onValueChange = {
                            viewModel.changeSearch(it)
                        },
                        placeholder = stringResource(Res.string.search),
                        modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)
                            .height(Constants.TextFieldHeight)
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        }
                    )
                    Spacer(Modifier.size(20.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableTab(
    modifier: Modifier = Modifier,
    text: String,
    isSelect: Boolean,
    select: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth().background(
            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.grey400,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank {
            select()
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, style = MaxiPulsTheme.typography.regular.copy(
                lineHeight = 14.sp,
                fontSize = 14.sp,
                color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
            )
        )
    }
}

@Composable
private fun SportsmanCard(
    modifier: Modifier = Modifier,
    number: Int,
    name: String,
    lastname: String,
    middleName: String,
    age: Int,
    heartRateMax: Int,
    avatar: String,
    onClick: () -> Unit,
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
                    ).clip(CircleShape).size((60.0 / division).dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatar.isBlank()) {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            modifier = Modifier.size(
                                width = (25 / division).dp,
                                (32 / division).dp
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

                Column(modifier = Modifier) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = number.toString(), style = MaxiPulsTheme.typography.bold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                        Text(
                            text = "${lastname}. ${
                                name.firstOrNull()?.toString().orEmpty()
                            }. ${middleName.firstOrNull()?.toString().orEmpty()}.",
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Spacer(modifier = Modifier.size((10 / spacerDivision).dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(Res.string.age)}: ${
                                stringResource(
                                    Res.string.age_text,
                                    age
                                )
                            }",
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.size((5 / spacerDivision).dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(Res.string.heart_rate_peak_player)}: $heartRateMax",
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size((20 / spacerDivision).dp))

    }

}