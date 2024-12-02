package org.example.project.screens.tests

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.tests
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import maxipuls.composeapp.generated.resources.choose_type_activity
import maxipuls.composeapp.generated.resources.reputation
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.start_test
import maxipuls.composeapp.generated.resources.test_desc
import org.example.project.domain.model.test.TestUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.main.MainScreen
import org.example.project.screens.mainTab.tabs.MainTab
import org.example.project.screens.root.RootNavigator
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource

class TestsScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            TestsViewModel()
        }
        val rootNavigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(
            modifier = Modifier,
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (state.selectTestUI == null) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)) {
//                        Image(
//                            painter = painterResource(Res.drawable.test_background),
//                            contentDescription = null,
//                            contentScale = ContentScale.FillWidth
//                        )
                        Text(
                            text = stringResource(Res.string.tests),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                        Text(
                            text = stringResource(Res.string.choose_type_activity),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.textDropDown
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).widthIn(max = 500.dp)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.tests),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                        )
                        Spacer(Modifier.size(20.dp))
                        Text(
                            text = stringResource(Res.string.test_desc),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.weight(1f))
                        MaxiButton(
                            onClick = {
                                tabNavigator.current = MainTab(state.selectTestUI)
                            },
                            enabled = state.selectTestUI != null,
                            text = stringResource(Res.string.start_test),
                            modifier = Modifier.fillMaxWidth().height(44.dp)
                        )
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Column(modifier = Modifier.weight(0.4f, false)) {
                    Spacer(Modifier.size(20.dp))
                    MaxiOutlinedTextField(
                        value = state.search,
                        onValueChange = {
                            viewModel.changeSearch(it)
                        },
                        placeholder = stringResource(Res.string.search),
                        modifier = Modifier.fillMaxWidth().height(Constants.TextFieldHeight)
                            .padding(horizontal = 20.dp),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.search),
                                modifier = Modifier.size(20.dp),
                                contentDescription = null
                            )
                        })
                    Spacer(Modifier.size(20.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.tests) {
                            TestItem(modifier = Modifier.fillMaxWidth(), testUI = it) {
                                viewModel.changeTests(it)
                            }
                        }
                    }

                }
            }

        }
    }
}

@Composable
internal fun TestItem(
    modifier: Modifier = Modifier,
    testUI: TestUI,
    onClick: () -> Unit,
) {
    Row(
        modifier.then(
            if (testUI.isPay) Modifier.background(
                brush = Brush.linearGradient(
                    listOf(
                        androidx.compose.ui.graphics.Color(0xFFFFA93A),
                        androidx.compose.ui.graphics.Color(0xFFE81F61),
                        androidx.compose.ui.graphics.Color(0xFF3093F9)
                    ),
                ),
                shape = RoundedCornerShape(25.dp)
            ).background(
                color = MaxiPulsTheme.colors.uiKit.white.copy(alpha = 0.3f),
                shape = RoundedCornerShape(25.dp)
            ) else Modifier.background(
                color = MaxiPulsTheme.colors.uiKit.card,
                shape = RoundedCornerShape(25.dp)
            )
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
        }.animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(20.dp))
        Icon(
            modifier = Modifier.padding(vertical = 15.dp).size(24.dp),
            painter = painterResource(testUI.icon),
            contentDescription = null,
            tint = if (testUI.isPay) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
        )
        Text(
            text = stringResource(testUI.title),
            modifier = Modifier.padding(start = 10.dp).weight(1f),
            style = MaxiPulsTheme.typography.semiBold.copy(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = if (testUI.isPay) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
            )
        )
//        Icon(
//            modifier = Modifier.size(24.dp),
//            painter = painterResource(Res.drawable.reputation),
//            contentDescription = null,
//            tint = if (testUI.isPay) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
//        )
        Spacer(Modifier.size(20.dp))
    }
}