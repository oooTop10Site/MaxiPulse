package org.example.project.screens.tablet.settings.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_large_ic
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.city
import maxipuls.composeapp.generated.resources.description
import maxipuls.composeapp.generated.resources.high_performance_ble
import maxipuls.composeapp.generated.resources.save
import maxipuls.composeapp.generated.resources.settings
import maxipuls.composeapp.generated.resources.title
import maxipuls.composeapp.generated.resources.use_route
import org.example.project.ext.clickableBlank
import org.example.project.ext.granted
import org.example.project.platform.ImagePicker
import org.example.project.platform.permission.model.Permission
import org.example.project.screens.tablet.settings.SettingSelectItem
import org.example.project.screens.tablet.settings.SettingsSelectableItem
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class SettingEditScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            SettingEditViewModel()
        }
        val navigator = LocalNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        var imagePickerShow by remember { mutableStateOf(false) }
        var imagePickerPermission by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        viewModel.imagePermissionsService.checkPermissionFlow(Permission.READ_EXTERNAL_STORAGE)
            .collectAsState(viewModel.imagePermissionsService.checkPermission(Permission.READ_EXTERNAL_STORAGE))
            .granted {
                if (imagePickerPermission) {
                    imagePickerShow = true
                }
            }
        MaxiPageContainer(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarTitle(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                text = stringResource(Res.string.settings),
                showCurrentTime = false,
            )
        }) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.size(200.dp).clickableBlank {
                                scope.launch {
                                    if (viewModel.imagePermissionsService.checkPermission(Permission.READ_EXTERNAL_STORAGE)
                                            .granted()
                                    ) {
                                        imagePickerShow = true
                                    } else {
                                        imagePickerPermission = true
                                        viewModel.imagePermissionsService.providePermission(
                                            Permission.READ_EXTERNAL_STORAGE
                                        )
                                    }

                                }
                            },
                        ) {
                            if (state.sportsmanUI.avatar.isBlank()) {
                                Box(
                                    modifier = Modifier.fillMaxSize().background(
                                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                        shape = CircleShape
                                    ).clip(CircleShape).align(Alignment.Center)
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.add_large_ic),
                                        modifier = Modifier.size(
                                            100.dp
                                        ).align(Alignment.Center),
                                        colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                        contentDescription = null
                                    )
                                }
                            } else {
                                MaxiImage(
                                    modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                        .clip(CircleShape),
                                    url = state.sportsmanUI.avatar,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size((30).dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.title),
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    ),
                                    modifier = Modifier.width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                MaxiOutlinedTextField(
                                    modifier = Modifier.width(300.dp).height(
                                        Constants.TextFieldHeight
                                    ), onValueChange = {
                                        viewModel.changeTitle(it)
                                    }, value = state.title
                                )
                            }

                            Spacer(modifier = Modifier.size((10).dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.description),
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    ),
                                    modifier = Modifier.width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                MaxiOutlinedTextField(
                                    modifier = Modifier.width(300.dp).height(
                                        Constants.TextFieldHeight
                                    ), onValueChange = {
                                        viewModel.changeDescription(it)
                                    }, value = state.desc
                                )
                            }
                            Spacer(modifier = Modifier.size((10).dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.city),
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    ),
                                    modifier = Modifier.width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                MaxiOutlinedTextField(
                                    modifier = Modifier.width(300.dp).height(
                                        Constants.TextFieldHeight
                                    ), onValueChange = {
                                        viewModel.changeCity(it)
                                    }, value = state.city
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(end = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(23.dp)
                    ) {
                        MaxiOutlinedButton(
                            onClick = {
                                navigator.pop()
                            },
                            shape = RoundedCornerShape(100.dp),
                            text = stringResource(Res.string.cancel),
                            modifier = Modifier.size(width = 200.dp, height = 54.dp)
                        )

                        MaxiButton(
                            onClick = {
                                navigator.pop()
                            },
                            shape = RoundedCornerShape(100.dp),
                            text = stringResource(Res.string.save),
                            modifier = Modifier.size(width = 200.dp, height = 54.dp)
                        )
                    }
                }
                Spacer(Modifier.size(20.dp))
                Column(modifier = Modifier.alpha(0.4f)) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    state.tabs.forEach { item ->
                        Spacer(Modifier.size(20.dp))
                        SettingSelectItem(
                            modifier = Modifier.fillMaxWidth(),
                            isSelect = false,
                            item = item,
                            viewModel = null
                        ) {
                        }
                        Spacer(Modifier.size(20.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                    }
                    Spacer(Modifier.size(30.dp))

                    SettingsSelectableItem(
                        modifier = Modifier.fillMaxWidth(),
                        checked = state.useRoute,
                        text = stringResource(Res.string.use_route),
                        onChange = {
                        })
                    Spacer(Modifier.size(20.dp))

                    SettingsSelectableItem(
                        modifier = Modifier.fillMaxWidth(),
                        checked = state.useHighPerformance,
                        text = stringResource(Res.string.high_performance_ble),
                        onChange = {
                        })
                    Spacer(Modifier.size(30.dp))
                }
            }
        }

        ImagePicker(imagePickerShow) { image ->
            imagePickerPermission = false
            imagePickerShow = false
            val pathChosen = image ?: return@ImagePicker
            viewModel.changeLogo(pathChosen.path)
        }
    }
}