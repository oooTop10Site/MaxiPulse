package org.example.project.screens.adaptive.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.background_auth
import maxipuls.composeapp.generated.resources.lock
import maxipuls.composeapp.generated.resources.logo
import maxipuls.composeapp.generated.resources.logo_minipulse
import maxipuls.composeapp.generated.resources.user
import org.example.project.domain.model.ButtonActions
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.mainTab.MainTabScreen
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiTextField
import org.example.project.utils.debouncedClick
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

class LoginScreen : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val screenSize = ScreenSize.currentOrThrow
        val viewModel = rememberScreenModel {
            LoginViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val textOfPersonalData = buildAnnotatedString {
            withStyle(
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                ).toSpanStyle()
            ) {
                append("Создавая или входя в аккаунт, я соглашаюсь с ")
            }
            withStyle(
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.primary,
                    textDecoration = TextDecoration.Underline
                ).toSpanStyle()
            ) {
                append("Политикой в отношении обработки персональных данных")
            }
            withStyle(
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                ).toSpanStyle()
            ) {
                append(" и ")
            }
            withStyle(
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    color = MaxiPulsTheme.colors.uiKit.primary,
                    textDecoration = TextDecoration.Underline
                ).toSpanStyle()
            ) {
                append("Пользовательским соглашением")
            }
        }
        LaunchedEffect(viewModel) {
            launch {
                viewModel.container.sideEffectFlow.collect {
                    when (it) {
                        LoginEvent.Success -> {
                            rootNavigator.replaceAll(MainTabScreen())
                        }
                    }
                }
            }
        }
        val state = viewModel.stateFlow.collectAsState()
        MaxiPageContainer {
            when (screenSize.widthSizeClass) {
                WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                    TabletContent(viewModel, state, textOfPersonalData)
                }

                WindowWidthSizeClass.Expanded -> {
                    DesktopContent(viewModel, state, textOfPersonalData)
                }
            }
        }
    }
    @Composable
    private fun TabletContent(
        viewModel: LoginViewModel,
        state: State<LoginState>,
        textOfPersonalData: AnnotatedString
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = imageResource(resource = Res.drawable.background_auth),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    bitmap = imageResource(resource = Res.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier.width(400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    LoginTextField(value = state.value.login) {
                        viewModel.changeLogin(it)
                    }

                    PasswordTextField(
                        value = state.value.password,
                        isShowPassword = state.value.isShowPassword,
                        changePasswordVisible = {
                            viewModel.changeShowPassword()
                        },
                        change = {
                            viewModel.changePassword(it)
                        },
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().clickableBlank {
                            viewModel.changeRememberMe()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MaxiCheckbox(
                            checked = state.value.rememberMe,
                            onCheckedChange = {
                                viewModel.changeRememberMe()
                            },
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "Запомнить меня?",
                            modifier = Modifier.padding(start = 20.dp),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                            )
                        )
                    }
                }

                MaxiButton(
                    onClick = debouncedClick(){
                        viewModel.login()
                    },
                    text = "Войти",
                    modifier = Modifier.height(54.dp),
                )

                Text(
                    text = textOfPersonalData,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }

    @Composable
    private fun DesktopContent(
        viewModel: LoginViewModel,
        state: State<LoginState>,
        textOfPersonalData: AnnotatedString
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = imageResource(resource = Res.drawable.background_auth),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    bitmap = imageResource(resource = Res.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier.width(400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    LoginTextField(value = state.value.login) {
                        viewModel.changeLogin(it)
                    }

                    PasswordTextField(
                        value = state.value.password,
                        isShowPassword = state.value.isShowPassword,
                        changePasswordVisible = {
                            viewModel.changeShowPassword()
                        },
                        change = {
                            viewModel.changePassword(it)
                        },
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().clickableBlank {
                            viewModel.changeRememberMe()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MaxiCheckbox(
                            checked = state.value.rememberMe,
                            onCheckedChange = {
                                viewModel.changeRememberMe()
                            },
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "Запомнить меня?",
                            modifier = Modifier.padding(start = 20.dp),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                            )
                        )
                    }
                }

                MaxiButton(
                    onClick = debouncedClick(){
                        viewModel.login()
                    },
                    text = "Войти",
                    modifier = Modifier.height(54.dp),
                )

                Text(
                    text = textOfPersonalData,
                    modifier = Modifier.width(600.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }

    @Composable
    private fun LoginTextField(
        value: String,
        change: (String) -> Unit,
    ) {
        MaxiTextField(
            leadingIcon = {
                Icon(
                    bitmap = imageResource(Res.drawable.user),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )
            },
            value = value,
            onValueChange = {
                change(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Логин"
        )
    }

    @Composable
    private fun PasswordTextField(
        value: String,
        isShowPassword: Boolean,
        changePasswordVisible: () -> Unit,
        change: (String) -> Unit,
    ) {
        MaxiTextField(
            leadingIcon = {
                Icon(
                    bitmap = imageResource(Res.drawable.lock),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )
            },
            value = value,
            onValueChange = {
                change(it)
            },
//            trailingIcon = {
//                Icon(bitmap = Icons.Filled, contentDescription = null, Modifier.clickableBlank {
//                    changePasswordVisible()
//                })
//            },
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Пароль"
        )
    }
}