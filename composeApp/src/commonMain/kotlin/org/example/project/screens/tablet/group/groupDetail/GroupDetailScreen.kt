package org.example.project.screens.tablet.group.groupDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.close_solid_ic
import maxipuls.composeapp.generated.resources.pencil
import org.example.project.ext.clickableBlank
import org.example.project.screens.tablet.group.groupEdit.GroupEditScreen
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.sportsman.components.SportsmanCard
import org.example.project.screens.tablet.sportsman.detail.SportsmanDetailScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.chunked

class GroupDetailScreen(private val groupId: String) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            GroupDetailViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> 1
            WindowWidthSizeClass.Expanded -> 2
            WindowWidthSizeClass.Compact -> 1
            else -> 1
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData(groupId)
        }

        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        MaxiPulsTheme.colors.uiKit.primary,
                                        shape = CircleShape
                                    )
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

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(40.dp)
                            ) {
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
                                        painterResource(Res.drawable.pencil),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp).clickableBlank(onClick = debouncedClick() {
                                            rootNavigator.push(GroupEditScreen(groupId = groupId))
                                        }),
                                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                    )
                                }
//                            Box(
//                                modifier = Modifier.size(40.dp)
//                                    .background(
//                                        MaxiPulsTheme.colors.uiKit.primary,
//                                        shape = CircleShape
//                                    )
//                                    .clip(CircleShape),
//                                contentAlignment = Alignment.Center
//                            ) {
                                Icon(
                                    painterResource(Res.drawable.close_solid_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp).clickableBlank {
                                        //todo я хз чо тут надо
//                                        rootNavigator.push(GroupEditScreen(groupId = groupId))
                                    },
                                    tint = MaxiPulsTheme.colors.uiKit.primary
                                )
                            }
//                        }
                        }
                        Text(
                            text = state.groupUI.title,
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.align(Alignment.Center),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
                                showEditIcon = false,
                                onClick = debouncedClick() {
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
    }
}