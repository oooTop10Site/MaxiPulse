package org.example.project.screens.tests.readiesForUpload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.arrow_right
import maxipuls.composeapp.generated.resources.cone_ic
import maxipuls.composeapp.generated.resources.shuttle_run
import org.example.project.ext.formatSeconds
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.test_end

class ReadiesForUploadScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            ReadiesForUploadViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.readies_for_upload),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                    )
                    Spacer(Modifier.size(20.dp))
                    Row(
                        Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.time.formatSeconds(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.primary
                            ),
                        )
                    }
                    Spacer(Modifier.size(20.dp))
                }
            }
        ) {

        }
    }
}