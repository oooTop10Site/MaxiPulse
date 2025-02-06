package org.example.project.screens.tablet.options.Questionnaire

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.activity_avoidance
import maxipuls.composeapp.generated.resources.date
import maxipuls.composeapp.generated.resources.description
import maxipuls.composeapp.generated.resources.fear_active
import maxipuls.composeapp.generated.resources.fio
import maxipuls.composeapp.generated.resources.group
import maxipuls.composeapp.generated.resources.level_anxiety_mood
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.sportsman_fio
import org.example.project.domain.model.questionnaire.SportsmanQuestionnaireUI
import org.example.project.ext.toUI
import org.example.project.screens.tablet.tests.TestItem
import org.example.project.screens.tablet.training.trainingResult.RegularResultBox
import org.example.project.screens.tablet.training.trainingResult.TitleResultBox
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class QuestionnaireResultScreen : Screen {
    @Composable
    override fun Content() {
        val rootNavigator = RootNavigator.currentOrThrow
        val viewModel = rememberScreenModel { QuestionnaireResultViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(0.25f)) {
                    Spacer(Modifier.size(22.dp))
                    BackIcon(modifier = Modifier.padding(start = 20.dp).size(40.dp)) {
                        rootNavigator.pop()
                    }
                    Spacer(Modifier.size(10.dp))

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.types) {
                            TestItem(
                                modifier = Modifier.fillMaxWidth().height(45.dp),
                                title = stringResource(it.title),
                                isPay = false,
                                isSelect = it == state.currentType
                            ) {
                                viewModel.changeType(it)
                            }
                        }
                    }
                }
                VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp)
                Column(modifier = Modifier.weight(0.75f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        MaxiTextFieldMenu<String>(
                            text = state.selectGroup.orEmpty(),
                            currentValue = state.selectGroup.orEmpty(),
                            onChangeWorkScope = {
                                viewModel.changeGroup(it)
                            },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.group),
                            items = state.groups,
                            itemToString = {
                                it
                            },
                        )
                        Spacer(Modifier.size(20.dp))

                        MaxiTextFieldMenu<SportsmanQuestionnaireUI?>(
                            text = state.selectSportsman?.fio.orEmpty(),
                            currentValue = state.selectSportsman,
                            onChangeWorkScope = {
                                viewModel.changeSportsman(it)
                            },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.group),
                            items = state.sportsmans,
                            itemToString = {
                                it?.fio.orEmpty()
                            },
                        )
                        Spacer(Modifier.size(20.dp))

                        MaxiTextFieldMenu<String>(
                            text = state.selectGroup.orEmpty(),
                            currentValue = state.selectGroup.orEmpty(),
                            onChangeWorkScope = {
                                viewModel.changeGroup(it)
                            },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(1f),
                            placeholderText = stringResource(Res.string.group),
                            items = state.groups,
                            itemToString = {
                                it
                            },
                        )
                        Spacer(Modifier.size(80.dp))
                        MaxiButton(
                            modifier = Modifier.weight(1f).height(40.dp),
                            onClick = debouncedClick {},
                            text = stringResource(Res.string.description)
                        )

                    }
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().height(72.dp)) {
                            TitleResultBox(
                                modifier = Modifier.width(150.dp).fillMaxHeight(),
                                text = stringResource(Res.string.sportsman_fio),
                                maxLines = 2
                            )
                            val strings = listOf<StringResource>(
                                Res.string.fear_active,
                                Res.string.activity_avoidance,
                                Res.string.level_anxiety_mood,
                                Res.string.date,
                            )
                            strings.forEach {
                                VerticalDivider(
                                    modifier = Modifier.fillMaxHeight(),
                                    color = MaxiPulsTheme.colors.uiKit.divider
                                )
                                TitleResultBox(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    color = MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.1f),
                                    text = stringResource(it),
                                    maxLines = 3
                                )
                            }

                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        LazyColumn(Modifier.weight(1f).fillMaxWidth()) {
                            items(state.sportsmans) {
                                CellItem(
                                    sportsmanUI = it
                                )
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaxiPulsTheme.colors.uiKit.divider
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CellItem(sportsmanUI: SportsmanQuestionnaireUI) {
        Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
            RegularResultBox(
                modifier = Modifier.width(150.dp).fillMaxHeight(),
                color = Color.Transparent,
                text = sportsmanUI.fio,
                maxLines = 2
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                color = sportsmanUI.fearColor,
                text = "${stringResource(sportsmanUI.fearText)} (${sportsmanUI.fear})",
                maxLines = 2
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                color = sportsmanUI.avoidColor,
                text = "${stringResource(sportsmanUI.avoidText)} (${sportsmanUI.avoid})",
                maxLines = 2
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                color = sportsmanUI.anxietyColor,
                text = "${stringResource(sportsmanUI.anxietyText)} (${sportsmanUI.anxiety})",
                maxLines = 2
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            RegularResultBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                color = Color.Transparent,
                text = sportsmanUI.date.toUI(),
                maxLines = 2
            )
        }
    }
}