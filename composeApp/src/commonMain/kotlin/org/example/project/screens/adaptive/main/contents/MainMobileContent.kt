package org.example.project.screens.adaptive.main.contents

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.appeared_new_task
import maxipuls.composeapp.generated.resources.burger
import maxipuls.composeapp.generated.resources.main
import maxipuls.composeapp.generated.resources.profile
import org.example.project.domain.model.task.MainTaskUI
import org.example.project.domain.model.test.TestUI
import org.example.project.ext.navigate
import org.example.project.screens.adaptive.main.MainState
import org.example.project.screens.adaptive.main.MainViewModel
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.debouncedClick
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun MainMobileContent(
    viewModel: MainViewModel,
    state: MainState,
    testUI: TestUI?
) {
    val navigator = RootNavigator.currentOrThrow
    MaxiPageContainerMobile(topBar = {
        TopBarMobile(
            modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
            title = stringResource(Res.string.main),
            leadingIcon = {
                IconButton(modifier = Modifier.size(44.dp), onClick = {
                    viewModel.openMenu()
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.burger),
                        modifier = Modifier.size(30.dp),
                        contentDescription = null,
                        tint = MaxiPulsTheme.colors.uiKit.textColor
                    )
                }
            })
    }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = safeAreaHorizontal()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(20.dp))
            Box(
                modifier = Modifier.background(
                    MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                    shape = CircleShape
                ).clip(CircleShape).size(50.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.avatar.isBlank()) {
                    Image(
                        painter = painterResource(Res.drawable.profile),
                        modifier = Modifier.size(
                            24.dp
                        ),
                        colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                        contentDescription = null
                    )
                } else {
                    MaxiImage(
                        modifier = Modifier.fillMaxSize(),
                        url = state.avatar,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(Modifier.size(5.dp))
            Text(
                text = "${state.lastname}\n${state.name} ${state.middleName}",
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    textAlign = TextAlign.Center

                )
            )

            TaskItem(
                modifier = Modifier.padding(top = 20.dp).height(80.dp),
                taskUI = state.currentTask,
                onClick = debouncedClick() {
                    state.currentTask.navigate(navigator = navigator)
                }
            )

            Spacer(Modifier.size(20.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                item {
                    Text(
                        text = stringResource(Res.string.appeared_new_task),
                        style = MaxiPulsTheme.typography.medium.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    )
                }
                items(state.task) {
                    TaskItem(
                        modifier = Modifier.height(80.dp),
                        taskUI = it,
                        onClick = debouncedClick() {
                            it.navigate(navigator = navigator)
                        })
                }
            }
        }

    }

}

@Composable
private fun TaskItem(modifier: Modifier = Modifier, taskUI: MainTaskUI, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 7.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaxiPulsTheme.colors.uiKit.white)
    ) {

        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(taskUI.image),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )

            Spacer(Modifier.size(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = taskUI.title, style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = taskUI.description, style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    )
                )
            }

        }

    }
}