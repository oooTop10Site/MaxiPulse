package org.example.project.screens.tablet.options

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.choose_type_activity
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.start_test
import maxipuls.composeapp.generated.resources.test_background
import maxipuls.composeapp.generated.resources.test_desc
import maxipuls.composeapp.generated.resources.tests
import org.example.project.screens.adaptive.mainTab.tabs.MainTab
import org.example.project.screens.tablet.tests.TestItem
import org.example.project.screens.tablet.tests.TestsViewModel
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class OptionsScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            OptionsViewModel()
        }
        val rootNavigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val state by viewModel.stateFlow.collectAsState()

        MaxiPageContainer(
            modifier = Modifier,
        ) {
            if (state.selectOption == null) {
                Image(
                    painter = painterResource(Res.drawable.test_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()

                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                if (state.selectOption == null) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)) {
                        Text(
                            text = stringResource(Res.string.tests),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.TopCenter),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(Res.string.choose_type_activity),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.textDropDown
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
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
                            },
                            enabled = state.selectOption != null,
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
                        value = "",
                        onValueChange = {
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
                        items(state.items) {
                            TestItem(
                                modifier = Modifier.fillMaxWidth().height(45.dp),
                                title = stringResource(it.title),
                                isPay = false,
                                isSelect = it == state.selectOption
                            ) {
                                viewModel.changeOption(it)
                            }
                        }
                    }

                }
            }

        }
    }


}