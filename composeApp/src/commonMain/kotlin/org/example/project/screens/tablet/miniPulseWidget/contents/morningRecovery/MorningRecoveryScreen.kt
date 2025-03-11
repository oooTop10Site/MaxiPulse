package org.example.project.screens.tablet.miniPulseWidget.contents.morningRecovery

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.profile
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.maxOf
import org.example.project.ext.toDayOfWeekFullDate
import org.example.project.screens.tablet.options.utp.graphs.MorningRecoveryGraph
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.NextIcon
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource

class MorningRecoveryScreen(private val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MorningRecoveryViewModel()
        }
        var isOpen by remember { mutableStateOf(false) }
        val state by viewModel.stateFlow.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.loadUsers()

        }

        MaxiPageContainer(modifier = modifier, topBar = {
            Column(modifier = Modifier.fillMaxWidth().height(90.dp)) {
                Spacer(Modifier.size(30.dp))
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.width(310.dp).align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackIcon(
                            modifier = Modifier.size(30.dp),
                            iconSize = 18.dp,
                            isDebounce = false
                        ) {
                            viewModel.decrementDate()
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = state.selectDate.toDayOfWeekFullDate(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.weight(1f))

                        NextIcon(
                            modifier = Modifier.size(30.dp),
                            iconSize = 18.dp,
                            isDebounce = false
                        ) {
                            viewModel.incrementDate()
                        }
                    }
                }
                Spacer(Modifier.size(30.dp))
            }
        }) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Adaptive(120.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.filterUsers) {
                        SportsmanMorningRecoveryItem(modifier = Modifier.height(160.dp), it) {
                            viewModel.changeIsExpand(it.sportsmanId)
                            isOpen = true
                        }
                    }
                }
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    val contentHeight by animateFloatAsState(
                        targetValue = if (isOpen) 0.6f else 0f,
                        animationSpec = tween(durationMillis = 200) // Длительность анимации в миллисекундах
                    )
                    Card(
                        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                        modifier = Modifier.size(width = 80.dp, height = 40.dp)
                            .align(Alignment.CenterHorizontally),
                        elevation = CardDefaults.elevatedCardElevation(10.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = MaxiPulsTheme.colors.uiKit.white)
                    ) {
                        val rotationAngle by animateFloatAsState(
                            targetValue = if (isOpen) 180f else 0f,
                            animationSpec = tween(durationMillis = 300) // Длительность анимации в миллисекундах
                        )
                        IconButton(
                            modifier = Modifier.size(40.dp).align(Alignment.CenterHorizontally),
                            onClick = {
                                isOpen = !isOpen
                            }) {
                            Icon(
                                painter = painterResource(Res.drawable.drop_ic),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .rotate(rotationAngle),
                                tint = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(contentHeight),
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = MaxiPulsTheme.colors.uiKit.white),
                        elevation = CardDefaults.elevatedCardElevation(10.dp)
                    ) {
                        Spacer(modifier = Modifier.size(20.dp))
                        MorningRecoveryGraph(
                            modifier = Modifier.weight(1f).fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            values = state.selectSportsman?.map { it.value }.orEmpty(),
                            days = state.currentWeek,
                            age = state.selectSportsman?.firstOrNull()?.age.orEmpty()
                        )
                        Spacer(modifier = Modifier.size(20.dp))

                    }
                }
            }
        }

    }

    @Composable
    private fun SportsmanMorningRecoveryItem(
        modifier: Modifier = Modifier,
        sportsmanUI: SportsmanTestResultUI,
        onClick: () -> Unit
    ) {
        Column(
            modifier = modifier.background(
                color = sportsmanUI.status.color,
                shape = RoundedCornerShape(25.dp)
            ).clip(RoundedCornerShape(25.dp)).clickableBlank {
                onClick()
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(14.dp))
            Box(modifier = Modifier.size(85.dp)) {
                Box(
                    modifier = Modifier.size(84.dp).clip(CircleShape)
                        .background(color = MaxiPulsTheme.colors.uiKit.white, shape = CircleShape)
                        .align(
                            Alignment.Center
                        ), contentAlignment = Alignment.Center
                ) {
                    if (sportsmanUI.image.isBlank()) {
                        Icon(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier.size(width = 35.dp, height = 45.dp),
                            tint = MaxiPulsTheme.colors.uiKit.grey800
                        )
                    } else {
                        MaxiImage(
                            url = sportsmanUI.image,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Image(
                    painter = painterResource(sportsmanUI.status.icon),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp).align(
                        Alignment.BottomEnd
                    )
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "${sportsmanUI.lastname}\n${sportsmanUI.name}",
                style = MaxiPulsTheme.typography.medium.copy(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor
                ),
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            Spacer(Modifier.weight(1f))

        }
    }
}