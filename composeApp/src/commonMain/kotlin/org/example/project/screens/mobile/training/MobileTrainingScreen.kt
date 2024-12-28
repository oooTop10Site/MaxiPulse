package org.example.project.screens.mobile.training

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.assigned_training
import maxipuls.composeapp.generated.resources.choose_sensor
import maxipuls.composeapp.generated.resources.put_on_the_sensor
import maxipuls.composeapp.generated.resources.shuttle_running
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.what_is_assigned_training
import org.example.project.domain.model.widget.WidgetSize
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.mobile.borgScale.MobileBackIcon
import org.example.project.screens.tablet.widget.PurpleGradientBackground
import org.example.project.screens.tablet.widget.UglyGradientBackground
import org.example.project.screens.tablet.widget.WidgetItem
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.MaxiRoundCheckBox
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class MobileTrainingScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MobileTrainingViewModel()
        }
        val navigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()

        MaxiPageContainerMobile(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarMobile(
                modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
                title = stringResource(Res.string.assigned_training),
                leadingIcon = {
                    MobileBackIcon(modifier = Modifier.size(40.dp)) {
                        navigator.pop()
                    }
                }
            )
        }) {
            Text(
                text = stringResource(Res.string.put_on_the_sensor),
                style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 40.sp,
                    lineHeight = 50.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColorWithAlpha,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal())
                    .align(Alignment.Center)
            )
            WhatIsTrainingCard(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).height(110.dp)
                    .padding(horizontal = safeAreaHorizontal()).align(Alignment.TopCenter),
            )
            MaxiButton(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = safeAreaHorizontal(), vertical = 20.dp).height(40.dp)
                    .align(Alignment.BottomCenter),
                onClick = {},
                text = stringResource(
                    Res.string.start
                )
            )

        }
    }
}


@Composable
fun SelectSensorAlertDialog(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    onDismiss: () -> Unit,
    success: () -> Unit
) {

    MaxiAlertDialog(
        modifier = Modifier.fillMaxWidth().height(404.dp)
            .padding(horizontal = safeAreaHorizontal()),
        paddingValues = PaddingValues(20.dp),
        onDismiss = {
            onDismiss()
        },
    ) {
        Text(
            text = stringResource(Res.string.choose_sensor),
            style = MaxiPulsTheme.typography.medium.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor,
            ),
        )

    }
}

@Composable
fun WhatIsTrainingCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp, hoveredElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PurpleGradientBackground(modifier = Modifier.fillMaxSize())
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val sizeText = 14.sp
                Image(
                    painter = painterResource(Res.drawable.shuttle_running),
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    text = stringResource(Res.string.what_is_assigned_training),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = sizeText,
                        lineHeight = sizeText,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                        textAlign = TextAlign.Center,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )


            }
        }
    }
}