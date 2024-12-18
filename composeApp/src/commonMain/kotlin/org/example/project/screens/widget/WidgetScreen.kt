package org.example.project.screens.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue

class WidgetScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            WidgetViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer() {

        }
    }
}

@Composable
fun WidgetItem(
    modifier: Modifier = Modifier,
    backgroundIcon: DrawableResource,
    title: String,
    icon: DrawableResource,
    onClick: () -> Unit,
) {
    Card(modifier = modifier, onClick = {
        onClick()
    }) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(backgroundIcon),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title, style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    )
                )

                Image(
                    painter = painterResource(icon),
                    modifier = Modifier,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )

            }
        }
    }
}