package org.example.project.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.attention
import maxipuls.composeapp.generated.resources.attention_user_with_not_active_sensor_desc
import maxipuls.composeapp.generated.resources.back
import maxipuls.composeapp.generated.resources.background_auth
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.choose_sensor
import maxipuls.composeapp.generated.resources.connect
import maxipuls.composeapp.generated.resources.connecting
import maxipuls.composeapp.generated.resources.error_ic
import maxipuls.composeapp.generated.resources.logo
import maxipuls.composeapp.generated.resources.marker_question
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.on_active_sensors
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.sensor_already_assigned
import maxipuls.composeapp.generated.resources.sensor_already_assigned_desc
import maxipuls.composeapp.generated.resources.settings_ic
import maxipuls.composeapp.generated.resources.start_tarining
import maxipuls.composeapp.generated.resources.start_test
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active_desc
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.test.TestUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.main.components.SportsmanSensorCard
import org.example.project.screens.main.contents.MainDesktopContent
import org.example.project.screens.main.contents.MainMobileContent
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.root.ScreenSize
import org.example.project.screens.tests.readiesForUpload.ReadiesForUploadScreen
import org.example.project.screens.tests.shuttleRun.ShuttleRunScreen
import org.example.project.screens.training.TrainingScreen
import org.example.project.screens.widget.WidgetScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class MainScreen(val testUI: TestUI? = null) : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MainViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> {
                MainDesktopContent(viewModel, state, testUI)
            }
            WindowWidthSizeClass.Expanded -> {
                MainDesktopContent(viewModel, state, testUI)
            }
            WindowWidthSizeClass.Compact -> {
                MainMobileContent(viewModel, state, testUI)
            }
            else -> {}
        }
    }
}