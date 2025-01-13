package org.example.project.screens.tablet.sportsman

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.filter
import maxipuls.composeapp.generated.resources.grid_ic
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.sportsman
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.sportsman.components.SportsmanCard
import org.example.project.screens.tablet.sportsman.detail.SportsmanDetailScreen
import org.example.project.screens.tablet.sportsman.edit.SportsmanEditScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class SportsmanScreen: Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            SportsmanViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when  {
            !state.isGrid -> 1
            screenSize.widthSizeClass == WindowWidthSizeClass.Medium -> 1
            screenSize.widthSizeClass == WindowWidthSizeClass.Expanded -> 2
            screenSize.widthSizeClass == WindowWidthSizeClass.Compact -> 1
            else -> 1
        }

        LaunchedEffect(viewModel) {
            viewModel.loadSportmans()
        }

        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    TopBarTitle(text = stringResource(Res.string.sportsman), showCurrentTime = true, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))
                    Spacer(
                        Modifier.size(20.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        MaxiOutlinedTextField(
                            value = state.search,
                            onValueChange = {
                                viewModel.changeSearch(it)
                            },
                            placeholder = stringResource(Res.string.search),
                            modifier = Modifier.height(40.dp).weight(1f),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                        )

                        MaxiTextFieldMenu<String>(
                            currentValue = state.filterSportsman,
                            text = state.filterSportsman,
                            onChangeWorkScope = { it ->
                                viewModel.changeSportsman(it)
                            },
                            items = state.filterSportsmans,
                            itemToString = { it },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(0.7f),
                            placeholderText = stringResource(Res.string.filter)
                        )

                        Icon(
                            if(state.isGrid) painterResource(Res.drawable.grid_ic) else  painterResource(Res.drawable.rectangle_listv2),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp).clickableBlank {
                                viewModel.changeIsGrid()
                            },
                            tint = MaxiPulsTheme.colors.uiKit.textColor,
                        )

                        Box(
                            modifier = Modifier.background(
                                MaxiPulsTheme.colors.uiKit.primary,
                                shape = CircleShape
                            ).clip(CircleShape).size(40.dp), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.add_ic),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp).clickableBlank {
                                   rootNavigator.push(SportsmanEditScreen())
                                },
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }
                    }

                    Spacer(
                        Modifier.size(20.dp)
                    )


                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(state.sportsmans.chunked(chunkSize)) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        chunk.forEach {
                            SportsmanCard(
                                modifier = Modifier.weight(1f),
                                name = it.name,
                                number = it.number,
                                middleName = it.middleName,
                                lastname = it.lastname,
                                age = it.age,
                                height = it.height,
                                weight = it.weight,
                                avatar = it.avatar,
                                isMale = it.isMale,
                                onClick = debouncedClick() {
                                    rootNavigator.push(SportsmanDetailScreen(it.id))
                                }
                            ) {
                                rootNavigator.push(SportsmanEditScreen(it.id))
                            }
                        }
                        if (chunk.size != chunkSize) {
                            for (i in 1..chunkSize - chunk.size) {
                                Box(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

        }
    }
}