package org.example.project.screens.tablet.group.groupEdit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.attention
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.close_ic
import maxipuls.composeapp.generated.resources.delete_group_content
import maxipuls.composeapp.generated.resources.no
import maxipuls.composeapp.generated.resources.save
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.title_group
import maxipuls.composeapp.generated.resources.trash
import maxipuls.composeapp.generated.resources.yes
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.sportsman.components.SportsmanCard
import org.example.project.screens.tablet.sportsman.detail.SportsmanDetailScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiOutlinedButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class GroupEditScreen(private val groupId: String) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            GroupEditViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        var isOpen by remember {
            mutableStateOf(false)
        }
        val chunkSize = when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> 1
            WindowWidthSizeClass.Expanded -> 2
            WindowWidthSizeClass.Compact -> 1
            else -> 1
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData(groupId)
            launch {
                viewModel.container.sideEffectFlow.collect {
                    when (it) {
                        GroupEditEvent.Success -> {
                            if (!isOpen) {
                                rootNavigator.pop()
                            } else {
                                isOpen = false
                                viewModel.loadData(groupId)
                            }
                        }

                        GroupEditEvent.SuccessDelete -> {
                            rootNavigator.popUntilRoot()
                        }

                        GroupEditEvent.SuccessNavBack -> {
                            rootNavigator.pop()
                        }
                    }
                }
            }
        }

        LaunchedEffect(isOpen) {
            if (isOpen) {
                viewModel.allSportsman()
            }
        }

        MaxiPageContainer(
            bottomBar = {
                Column() {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        MaxiOutlinedButton(
                            modifier = Modifier.height(54.dp).width(200.dp),
                            text = stringResource(Res.string.cancel),
                            onClick = {
                                rootNavigator.pop()
                            }
                        )

                        MaxiButton(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.height(54.dp).width(200.dp),
                            onClick = {
                                viewModel.save()
                            },
                            text = stringResource(Res.string.save)
                        )

                    }
                }
            },
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp)
                                .background(MaxiPulsTheme.colors.uiKit.primary, shape = CircleShape)
                                .clip(CircleShape).clickableBlank(role = Role.Button) {
                                    rootNavigator.pop()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.back_ic),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }

                        MaxiOutlinedTextField(
                            value = state.groupUI.title,
                            onValueChange = {
                                viewModel.changeGroupTitle(it)
                            },
                            placeholder = stringResource(Res.string.title_group),
                            modifier = Modifier.weight(1f).padding(horizontal = 40.dp).height(
                                Constants.TextFieldHeight
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (groupId.isNotBlank()) {
                                Box(
                                    modifier = Modifier.size(40.dp)
                                        .background(
                                            MaxiPulsTheme.colors.uiKit.primary,
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(Res.drawable.trash),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp).clickableBlank {
                                            viewModel.changeDeleteGroupAlert()
                                        },
                                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                    )
                                }
                            }
                            Spacer(Modifier.size(40.dp))
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        MaxiPulsTheme.colors.uiKit.primary,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(Res.drawable.add_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp).clickableBlank {
                                        isOpen = !isOpen
                                    },
                                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }
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
                items(
                    state.filteredSportsmans
                        .chunked(chunkSize)
                ) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth().animateItem(),
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
                                showEditIcon = true,
                                editIcon = {
                                    Icon(
                                        painterResource(Res.drawable.close_ic),
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp).clickableBlank() {
                                            viewModel.deleteSportsman(it.id)
                                        },
                                        tint = MaxiPulsTheme.colors.uiKit.primary
                                    )
                                },
                                onClick = {
                                    rootNavigator.push(SportsmanDetailScreen(it.id))
                                }
                            ) {
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
        SelectSportsmansDialog(
            existSportsmans = state.filteredSportsmans,
            sportsmans = state.allSportsmans,
            changeIsOpened = {
                isOpen = !isOpen
            },
            isOpen = isOpen,
            save = {
                viewModel.save(it)
            },
            text = state.search,
            changeText = {
                viewModel.changeSearch(it)
            }
        )
        if (state.deleteGroupAlert) {
            MaxiAlertDialog(
                modifier = Modifier.width(600.dp),
                title = stringResource(Res.string.attention),
                description = stringResource(Res.string.delete_group_content),
                alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                cancelText = stringResource(Res.string.no),
                acceptText = stringResource(Res.string.yes),
                accept = {
                    viewModel.deleteGroup(groupId)
                },
                onDismiss = {
                    viewModel.changeDeleteGroupAlert()
                }
            )
        }
    }
}

@Composable
internal fun SelectSportsmansDialog(
    existSportsmans: List<SportsmanUI>,
    sportsmans: List<SportsmanUI>,
    changeIsOpened: () -> Unit,
    isOpen: Boolean,
    text: String,
    save: (List<String>) -> Unit,
    changeText: (String) -> Unit,
) {
    val screenSize = ScreenSize.currentOrThrow
    val rootNavigator = RootNavigator.currentOrThrow
    val chunkSize = when (screenSize.widthSizeClass) {
        WindowWidthSizeClass.Medium -> 1
        WindowWidthSizeClass.Expanded -> 2
        WindowWidthSizeClass.Compact -> 1
        else -> 1
    }
    var selectSportsman: List<SportsmanUI> by remember {
        mutableStateOf(listOf())
    }
    LaunchedEffect(existSportsmans) {
        selectSportsman += existSportsmans
    }
    if (isOpen) {
        MaxiAlertDialog(
            modifier = Modifier.fillMaxWidth().padding(50.dp),
            descriptionContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MaxiOutlinedTextField(value = text, onValueChange = {
                        changeText(it)
                    }, modifier = Modifier.height(Constants.TextFieldHeight).fillMaxWidth(),
                        placeholder = stringResource(Res.string.search), trailingIcon = {
                            Icon(
                                painterResource(Res.drawable.search),
                                contentDescription = null,
                                tint = MaxiPulsTheme.colors.uiKit.textColor,
                                modifier = Modifier.size(20.dp)
                            )
                        })
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(vertical = 20.dp)
                    ) {
                        items(
                            sportsmans
                                .chunked(chunkSize)
                        ) { chunk ->
                            Row(
                                modifier = Modifier.fillMaxWidth().animateItem(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                chunk.forEach { item ->
                                    val isExits = item.id in selectSportsman.map { it.id }
                                    SportsmanCard(
                                        modifier = Modifier.weight(1f),
                                        name = item.name,
                                        number = item.number,
                                        middleName = item.middleName,
                                        lastname = item.lastname,
                                        age = item.age,
                                        height = item.height,
                                        weight = item.weight,
                                        avatar = item.avatar,
                                        isMale = item.isMale,
                                        showEditIcon = true,
                                        editIcon = {
                                            println("CLICK ")
                                            MaxiCheckbox(
                                                checked = isExits,
                                                onCheckedChange = {
                                                    selectSportsman = if (isExits) {
                                                        selectSportsman.filter { it.id != item.id }
                                                    } else {
                                                        selectSportsman + item
                                                    }
                                                })
                                        },
                                        onClick = {
                                            println("CLICK ")
                                            selectSportsman = if (isExits) {
                                                selectSportsman.filter { it.id != item.id }
                                            } else {
                                                selectSportsman + item
                                            }
                                        }
                                    ) {
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
            },
            onDismiss = {
                changeIsOpened()
            },
            accept = {
                save(selectSportsman.map { it.id })
            },
            alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
            acceptText = stringResource(Res.string.save),
            cancel = {
                changeIsOpened()
            },
            cancelText = stringResource(Res.string.cancel)
        )
    }
}