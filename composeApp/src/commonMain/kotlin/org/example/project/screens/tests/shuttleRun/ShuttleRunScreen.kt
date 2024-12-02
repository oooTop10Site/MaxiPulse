package org.example.project.screens.tests.shuttleRun

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.tests
import org.example.project.domain.model.test.ShuttleRunSportsmanUI
import org.example.project.ext.toUI
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.jetbrains.compose.resources.stringResource

class ShuttleRunScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            ShuttleRunViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()

        MaxiPageContainer(topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Text(
                    text = stringResource(Res.string.shuttle_run),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                )
                Spacer(Modifier.size(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))
                    Row(Modifier.weight(1f)) {
                        Text(
                            text = state.time.toUI(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.primary
                            ),
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.size(20.dp))
            }
        }, modifier = Modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MaxiButton(onClick = {}, text = stringResource(Res.string.start))
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.users) {
                        //todo
                    }
                }
                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun SportsmanShuttleTestItem(modifier: Modifier = Modifier, sportsmanUI: ShuttleRunSportsmanUI) {
    Column(
        modifier = modifier.background(
            color = sportsmanUI.status.color,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Text(
            text = "${sportsmanUI.lastname} ${sportsmanUI.firstname}",
            style = MaxiPulsTheme.typography.semiBold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = MaxiPulsTheme.colors.uiKit.lightTextColor
            ),
            maxLines = 2,
            modifier = Modifier.padding(top = 7.dp, start = 3.dp, end = 3.dp)
        )

        Box(
            modifier = Modifier.size(height = 49.dp, width = 94.dp)
                .background(
                    color = MaxiPulsTheme.colors.uiKit.white.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(25.dp)
                ).clip(RoundedCornerShape(25.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sportsmanUI.chss.toString(),
                style = MaxiPulsTheme.typography.semiBold.copy(
                    fontSize = 32.sp,
                    lineHeight = 32.sp,
                    color = MaxiPulsTheme.colors.uiKit.lightTextColor
                ),
                maxLines = 1,
                modifier = Modifier
            )
        }

        //todo кнопка по статусу
    }
}