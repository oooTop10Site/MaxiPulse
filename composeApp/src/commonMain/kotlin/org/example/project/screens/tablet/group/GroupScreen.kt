package org.example.project.screens.tablet.group

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.composition
import maxipuls.composeapp.generated.resources.grid_ic
import maxipuls.composeapp.generated.resources.group
import maxipuls.composeapp.generated.resources.groups
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.search
import org.example.project.ext.clickableBlank
import org.example.project.screens.tablet.group.components.CompositionCard
import org.example.project.screens.tablet.group.groupDetail.GroupDetailScreen
import org.example.project.screens.tablet.group.groupEdit.GroupEditScreen
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource

class GroupScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            GroupViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val rootNavigator = RootNavigator.currentOrThrow
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when {
            !state.isGrid -> 1
            screenSize.widthSizeClass == WindowWidthSizeClass.Medium -> 2
            screenSize.widthSizeClass == WindowWidthSizeClass.Expanded -> 2
            screenSize.widthSizeClass == WindowWidthSizeClass.Compact -> 1
            else -> 1
        }
        LaunchedEffect(viewModel) {
            viewModel.loadGroups()
        }
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    TopBarTitle(
                        text = stringResource(Res.string.groups),
                        showCurrentTime = true,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    )
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
                            currentValue = state.filterGroup,
                            text = state.filterGroup,
                            onChangeWorkScope = { it ->
                                viewModel.changeGroup(it)
                            },
                            items = state.filterGroups,
                            itemToString = { it },
                            modifier = Modifier.height(Constants.TextFieldHeight)
                                .weight(0.7f),
                            placeholderText = stringResource(Res.string.group)
                        )
                        Icon(
                            if (state.isGrid) painterResource(Res.drawable.grid_ic) else painterResource(
                                Res.drawable.rectangle_listv2
                            ),
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
                            ).clip(CircleShape).size(40.dp)
                                .clickableBlank(onClick = debouncedClick() {
                                    rootNavigator.push(GroupEditScreen(groupId = ""))
                                }), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.add_ic),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
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
                items(state.compositions.chunked(chunkSize)) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        chunk.forEach {
                            CompositionCard(
                                modifier = Modifier.weight(1f).height(100.dp),
                                title = it.title,
                                members = it.member,
                                onClick = debouncedClick() {
                                    rootNavigator.push(GroupDetailScreen(groupId = it.id))
                                },
                                onEdit = debouncedClick() {
                                    rootNavigator.push(GroupEditScreen(groupId = it.id))
                                }
                            )
                        }
                        if (chunk.size != chunkSize) {
                            for (i in 1..chunkSize - chunk.size) {
                                Box(modifier = Modifier.weight(1f).height(100.dp))
                            }
                        }
                    }
                }
            }

        }
    }
}