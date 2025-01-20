package org.example.project.screens.tablet.tests.readiesForUpload.result

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.share
import maxipuls.composeapp.generated.resources.zip
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ReadiesForUploadResultScreen(private val sportsmans: List<SportsmanTestResultUI>) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            ReadiesForUploadResultViewModel()
        }

        LaunchedEffect(viewModel) {
            viewModel.loadResult(sportmans = sportsmans)
        }

        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(Res.string.readies_for_upload),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        BackIcon(modifier = Modifier.size(40.dp).align(Alignment.CenterStart)) {
                            navigator.pop()
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        MaxiTextFieldMenu<String>(
                            items = state.filters,
                            itemToString = { it },
                            currentValue = state.filter,
                            onChangeWorkScope = {
                                viewModel.changeSelect(it)
                            },
                            modifier = Modifier.width(210.dp).height(Constants.TextFieldHeight),
                            text = state.filter,
                            placeholderText = ""
                        )
                        Spacer(Modifier.size(20.dp))
                        MaxiOutlinedTextField(
                            value = state.search,
                            onValueChange = {
                                viewModel.changeSearch(it)
                            },
                            modifier = Modifier.width(520.dp).height(Constants.TextFieldHeight),
                            placeholder = stringResource(Res.string.search),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            })
                        Spacer(Modifier.size(20.dp))
                        Box(
                            modifier = Modifier.height(Constants.TextFieldHeight).width(130.dp)
                                .clip(
                                    RoundedCornerShape(100.dp)
                                ).background(
                                    shape = RoundedCornerShape(100.dp),
                                    color = MaxiPulsTheme.colors.uiKit.grey400
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.share),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        }
                        Spacer(Modifier.size(20.dp))

                        Box(
                            modifier = Modifier.height(Constants.TextFieldHeight).width(130.dp)
                                .clip(
                                    RoundedCornerShape(100.dp)
                                ).background(
                                    shape = RoundedCornerShape(100.dp),
                                    color = MaxiPulsTheme.colors.uiKit.grey400
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.zip),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                }
            }
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(140.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(state.filterSportsmans) {
                    SportsmanTestResultItem(modifier = Modifier.height(190.dp), it) {

                    }
                }
            }
        }
    }
}


@Composable
fun SportsmanTestResultItem(
    modifier: Modifier = Modifier,
    sportsmanUI: SportsmanTestResultUI,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.background(
            color = sportsmanUI.status.color,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        Box(modifier = Modifier.size(120.dp)) {
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape)
                    .background(color = MaxiPulsTheme.colors.uiKit.white, shape = CircleShape)
                    .align(
                        Alignment.Center
                    ), contentAlignment = Alignment.Center
            ) {
                if (sportsmanUI.image.isBlank()) {
                    Icon(
                        painter = painterResource(Res.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.size(width = 42.dp, height = 54.dp),
                        tint = MaxiPulsTheme.colors.uiKit.grey800
                    )
                } else {
                    MaxiImage(
                        url = sportsmanUI.image,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Image(
                painter = painterResource(sportsmanUI.status.icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp).align(
                    Alignment.BottomEnd
                )
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = "${sportsmanUI.lastname}\n${sportsmanUI.name}",
            style = MaxiPulsTheme.typography.medium.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            ),
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(Modifier.weight(1f))

    }
}