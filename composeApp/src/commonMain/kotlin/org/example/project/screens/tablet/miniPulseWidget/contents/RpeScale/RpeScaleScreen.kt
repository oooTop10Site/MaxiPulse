package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.ext.toDayOfWeekFullDate
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.NextIcon
import androidx.compose.runtime.getValue
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.info_ic
import org.example.project.ext.toUI
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource

class RpeScaleScreen(private val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { RpeScaleViewModel() }
        val state by viewModel.stateFlow.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.loadRpies()
            viewModel.loadTrainings()
        }

        MaxiPageContainer(modifier = modifier, topBar = {
            Column(modifier = Modifier.fillMaxWidth().height(90.dp)) {
                Spacer(Modifier.size(30.dp))
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                    MaxiTextFieldMenu(
                        currentValue = state.filter,
                        items = state.filters,
                        onChangeWorkScope = {
                            viewModel.changeFilter(it)
                        },
                        modifier = Modifier.height(Constants.TextFieldHeight).width(210.dp).align(
                            Alignment.CenterStart
                        ),
                        text = state.filter,
                        placeholderText = "",
                        itemToString = { it }
                    )
                    Row(
                        modifier = Modifier.width(310.dp).align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackIcon(
                            modifier = Modifier.size(30.dp),
                            iconSize = 18.dp,
                            isDebounce = false
                        ) {
                            viewModel.decrementWeek()
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "${
                                state.selectWeek.firstOrNull()?.toUI().orEmpty()
                            } - ${state.selectWeek.lastOrNull()?.toUI().orEmpty()}",
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
                            viewModel.incrementWeek()
                        }
                    }

                    Icon(
                        painter = painterResource(Res.drawable.info_ic),
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.size(40.dp).align(Alignment.CenterEnd),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.size(30.dp))
            }
        }) {

            Row(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(modifier = Modifier.weight(1f)) {

                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Column(modifier = Modifier.weight(1f)) {
                    LazyColumn(modifier = Modifier.weight(1f)) {

                    }
                }

            }


        }
    }
}