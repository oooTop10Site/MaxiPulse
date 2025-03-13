package org.example.project.screens.tablet.miniPulseWidget.contents.remoteTraining

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.add_all
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.attention
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.create
import maxipuls.composeapp.generated.resources.create_training
import maxipuls.composeapp.generated.resources.description
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.heart_rate_peak_player
import maxipuls.composeapp.generated.resources.info_ic
import maxipuls.composeapp.generated.resources.member_training
import maxipuls.composeapp.generated.resources.people
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.put_away_all
import maxipuls.composeapp.generated.resources.sportsmen
import maxipuls.composeapp.generated.resources.stop_training_attention
import maxipuls.composeapp.generated.resources.title
import maxipuls.composeapp.generated.resources.yes
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.training.RemoteTrainingSportsmanStatus
import org.example.project.domain.model.training.RemoteTrainingStatus
import org.example.project.domain.model.training.RemoteTrainingUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.toUI
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.tests.shuttleRun.result.SportsmanResultCard
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.NextIcon
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource

class RemoteTrainingScreen(private val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { RemoteTrainingViewModel() }
        val state by viewModel.stateFlow.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.loadRemoteTraining()
            viewModel.loadSportsman()
        }

        MaxiPageContainer(modifier = modifier) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.size(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MaxiButton(
                        text = stringResource(Res.string.create_training),
                        buttonTextStyle = ButtonTextStyle.Bold,
                        modifier = Modifier.height(44.dp).width(264.dp),
                        onClick = {
                            viewModel.createTempTraining()
                        }
                    )

                    Icon(
                        painter = painterResource(Res.drawable.info_ic),
                        contentDescription = null,
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.size(40.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    items(state.remoteTrainings) {
                        RemoteTrainingItem(
                            modifier = Modifier.height(94.dp),
                            remoteTrainingUI = it
                        ) {
                            viewModel.selectTraining(it)
                        }
                    }
                }
            }
        }

        state.tempCreateRemoteTrainingUI?.let {
            CreateRemoteTraining(viewModel, state.sportsmen, it)
        }

        state.selectRemoteTraining?.let {
            RemoteTrainingInfo(viewModel, it)
        }

        if (state.selectUnFinishedRemoteTrainingId.isNotBlank()) {
            MaxiAlertDialog(
                modifier = Modifier.width(600.dp),
                title = stringResource(Res.string.attention),
                description = stringResource(Res.string.stop_training_attention),
                acceptText = stringResource(Res.string.yes),
                accept = {
                    viewModel.finish(isHard = true)
                },
                cancel = {
                    viewModel.selectUnFinishedRemoteTrainingId()
                },
                onDismiss = {
                    viewModel.selectUnFinishedRemoteTrainingId()
                },
                cancelText = stringResource(Res.string.cancel),
                alertDialogButtons = MaxiAlertDialogButtons.CancelAccept
            )
        }
    }

    @Composable
    private fun RemoteTrainingItem(
        modifier: Modifier = Modifier,
        remoteTrainingUI: RemoteTrainingUI,
        onClick: () -> Unit,
    ) {

        Row(
            modifier = modifier.background(
                color = MaxiPulsTheme.colors.uiKit.grey400,
                shape = RoundedCornerShape(25.dp)
            ).clip(RoundedCornerShape(25.dp)).clickableBlank { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(20.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Spacer(Modifier.weight(1f))
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${remoteTrainingUI.localDateTime.date.toUI()}  ${remoteTrainingUI.localDateTime.time.toUI()}",
                        style = MaxiPulsTheme.typography.regular.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.size(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(17.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = remoteTrainingUI.title,
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.size(20.dp))
                Text(
                    text = "${
                        remoteTrainingUI.members.size
                    } ${stringResource(Res.string.people)}",
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
            }
            Box(
                modifier = Modifier.fillMaxHeight().width(170.dp)
                    .background(color = remoteTrainingUI.status.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(remoteTrainingUI.status.title),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

    @Composable
    private fun CreateRemoteTraining(
        viewModel: RemoteTrainingViewModel,
        sportsmen: List<SportsmanUI>,
        tempRemoteTrainingUI: RemoteTrainingUI
    ) {
        MaxiAlertDialog(
            modifier = Modifier.fillMaxHeight(0.85f).fillMaxWidth(0.85f),
            alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
            acceptText = stringResource(Res.string.create),
            accept = {
                viewModel.saveTempTraining()
            },
            onDismiss = {
                viewModel.dismissTempTraining()
            },
            cancelText = stringResource(Res.string.cancel),
            cancel = {
                viewModel.dismissTempTraining()
            },
            paddingAfterTitle = false,
            descriptionContent = {
                Column(modifier = Modifier.fillMaxSize()) {
                    BackIcon(modifier = Modifier.size(40.dp)) { viewModel.dismissTempTraining() }
                    Spacer(Modifier.size(20.dp))
                    MaxiOutlinedTextField(
                        value = tempRemoteTrainingUI.title,
                        onValueChange = { viewModel.changeTitle(it) },
                        modifier = Modifier.height(
                            Constants.TextFieldHeight
                        ).fillMaxWidth(),
                        placeholder = stringResource(Res.string.title)
                    )
                    Spacer(Modifier.size(20.dp))
                    MaxiOutlinedTextField(
                        value = tempRemoteTrainingUI.desc,
                        onValueChange = { viewModel.changeDesc(it) },
                        modifier = Modifier.height(
                            Constants.TextFieldHeight
                        ).fillMaxWidth(),
                        placeholder = stringResource(Res.string.description)
                    )
                    Spacer(Modifier.size(20.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp).weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight().border(
                                width = 1.dp,
                                shape = RoundedCornerShape(25.dp),
                                color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.size(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(Res.string.sportsmen),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier.height(40.dp).background(
                                        color = MaxiPulsTheme.colors.uiKit.primary,
                                        shape = RoundedCornerShape(100.dp)
                                    ).clip(RoundedCornerShape(100.dp)).clickableBlank {
                                        sportsmen.filter { it.id !in tempRemoteTrainingUI.members.map { it.sportsmanUI.id } }
                                            .forEach {
                                                viewModel.selectMember(it)
                                            }
                                    },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.size(15.dp))
                                    Text(
                                        text = stringResource(Res.string.add_all),
                                        style = MaxiPulsTheme.typography.medium.copy(
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                            color = MaxiPulsTheme.colors.uiKit.lightTextColor
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.size(10.dp))

                                    Icon(
                                        painter = painterResource(Res.drawable.back_ic),
                                        contentDescription = null,
                                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                        modifier = Modifier.size(20.dp).rotate(180f)
                                    )


                                    Spacer(Modifier.size(15.dp))
                                }
                            }
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    vertical = 13.dp,
                                    horizontal = 20.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(sportsmen.filter { it.id !in tempRemoteTrainingUI.members.map { it.sportsmanUI.id } }) {
                                    SportsmanExistOrNoCard(
                                        modifier = Modifier.fillMaxWidth().height(80.dp),
                                        number = it.number,
                                        heartRateMax = it.chssMax,
                                        name = it.name,
                                        lastname = it.lastname,
                                        middleName = it.middleName,
                                        avatar = it.avatar,
                                        age = it.age,
                                        isSelect = false,
                                        isExist = it in tempRemoteTrainingUI.members.map { it.sportsmanUI },
                                        onAdd = {
                                            viewModel.selectMember(it)
                                        },
                                        onDelete = {
                                            viewModel.selectMember(it)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.size(40.dp))

                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight().border(
                                width = 1.dp,
                                shape = RoundedCornerShape(25.dp),
                                color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.size(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(Res.string.member_training),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Row(
                                    modifier = Modifier.height(40.dp).border(
                                        width = 1.dp,
                                        color = MaxiPulsTheme.colors.uiKit.primary,
                                        shape = RoundedCornerShape(100.dp)
                                    ).clip(RoundedCornerShape(100.dp)).clickableBlank {
                                        sportsmen.filter { it.id in tempRemoteTrainingUI.members.map { it.sportsmanUI.id } }
                                            .forEach {
                                                viewModel.selectMember(it)
                                            }
                                    },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.size(15.dp))
                                    Text(
                                        text = stringResource(Res.string.put_away_all),
                                        style = MaxiPulsTheme.typography.medium.copy(
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                            color = MaxiPulsTheme.colors.uiKit.primary
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.size(10.dp))

                                    Icon(
                                        painter = painterResource(Res.drawable.back_ic),
                                        contentDescription = null,
                                        tint = MaxiPulsTheme.colors.uiKit.primary,
                                        modifier = Modifier.size(20.dp)
                                    )


                                    Spacer(Modifier.size(15.dp))
                                }
                            }
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    vertical = 13.dp,
                                    horizontal = 20.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(tempRemoteTrainingUI.members) {
                                    SportsmanExistOrNoCard(
                                        modifier = Modifier.fillMaxWidth().height(80.dp),
                                        number = it.sportsmanUI.number,
                                        heartRateMax = it.sportsmanUI.chssMax,
                                        name = it.sportsmanUI.name,
                                        lastname = it.sportsmanUI.lastname,
                                        middleName = it.sportsmanUI.middleName,
                                        avatar = it.sportsmanUI.avatar,
                                        age = it.sportsmanUI.age,
                                        isSelect = false,
                                        isExist = it in tempRemoteTrainingUI.members,
                                        onAdd = {
                                            viewModel.selectMember(it.sportsmanUI)
                                        },
                                        onDelete = {
                                            viewModel.selectMember(it.sportsmanUI)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                }
            })
    }

    @Composable
    private fun RemoteTrainingInfo(
        viewModel: RemoteTrainingViewModel,
        selectRemoteTraining: RemoteTrainingUI
    ) {
        MaxiAlertDialog(
            modifier = Modifier.fillMaxHeight(0.85f).fillMaxWidth(0.85f),
            alertDialogButtons = if (selectRemoteTraining.status != RemoteTrainingStatus.End) MaxiAlertDialogButtons.Accept else null,
            acceptText = stringResource(Res.string.end),
            accept = {
                viewModel.finish()
            },
            onDismiss = {
                viewModel.dismissSelectTraining()
            },
            cancelText = stringResource(Res.string.cancel),
            cancel = {
                viewModel.dismissSelectTraining()
            },
            paddingAfterTitle = false,
            descriptionContent = {
                Column(modifier = Modifier.fillMaxSize()) {
                    BackIcon(modifier = Modifier.size(40.dp)) { viewModel.dismissSelectTraining() }
                    Spacer(Modifier.size(20.dp))
                    MaxiOutlinedTextField(
                        value = selectRemoteTraining.title,
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.height(
                            Constants.TextFieldHeight
                        ).fillMaxWidth(),
                        placeholder = stringResource(Res.string.title)
                    )
                    Spacer(Modifier.size(20.dp))
                    MaxiOutlinedTextField(
                        readOnly = true,
                        value = selectRemoteTraining.desc,
                        onValueChange = { },
                        modifier = Modifier.height(
                            Constants.TextFieldHeight
                        ).fillMaxWidth(),
                        placeholder = stringResource(Res.string.description)
                    )
                    Spacer(Modifier.size(20.dp))
                    Column(
                        modifier = Modifier.weight(1f).fillMaxWidth().border(
                            width = 1.dp,
                            shape = RoundedCornerShape(25.dp),
                            color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LazyVerticalGrid(
                            contentPadding = PaddingValues(
                                vertical = 20.dp,
                                horizontal = 20.dp
                            ),
                            columns = GridCells.Adaptive(400.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(selectRemoteTraining.members) {
                                SportsmanTrainingCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    number = it.sportsmanUI.number,
                                    heartRateMax = it.sportsmanUI.chssMax,
                                    name = it.sportsmanUI.name,
                                    lastname = it.sportsmanUI.lastname,
                                    middleName = it.sportsmanUI.middleName,
                                    avatar = it.sportsmanUI.avatar,
                                    age = it.sportsmanUI.age,
                                    isSelect = false,
                                    status = it.status
                                ) {
//                                        viewModel.changeSelectSportsman(it)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                }
            })
    }
}

@Composable
private fun SportsmanExistOrNoCard(
    modifier: Modifier = Modifier,
    number: Int,
    name: String,
    lastname: String,
    middleName: String,
    age: Int,
    heartRateMax: Int,
    avatar: String,
    isSelect: Boolean,
    isExist: Boolean,
    onDelete: () -> Unit,
    onAdd: () -> Unit,
) {
    val textColor =
        if (isSelect) MaxiPulsTheme.colors.uiKit.white else MaxiPulsTheme.colors.uiKit.textColor

    Column(
        modifier.background(
            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isExist) {
                Box(
                    modifier = Modifier.fillMaxHeight().width(60.dp)
                        .border(
                            width = 1.dp,
                            color = MaxiPulsTheme.colors.uiKit.primary,
                            shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp),
                        ).background(color = MaxiPulsTheme.colors.uiKit.white)
                        .clickableBlank {
                            onDelete()
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.back_ic),
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.size(36.dp),
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier.weight(1f).padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.background(
                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                        shape = CircleShape
                    ).clip(CircleShape).size(60.0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatar.isBlank()) {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            modifier = Modifier.size(
                                width = 25.dp,
                                32.dp
                            ),
                            colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                            contentDescription = null
                        )
                    } else {
                        MaxiImage(
                            modifier = Modifier.fillMaxSize(),
                            url = avatar,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))

                Column(modifier = Modifier) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = number.toString(), style = MaxiPulsTheme.typography.bold.copy(
                                color = textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "${lastname}. ${
                                name.firstOrNull()?.toString().orEmpty()
                            }. ${middleName.firstOrNull()?.toString().orEmpty()}.",
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(Res.string.age)}: ${
                                stringResource(
                                    Res.string.age_text,
                                    age
                                )
                            }",
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = textColor,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(Res.string.heart_rate_peak_player)}: $heartRateMax",
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = textColor,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (!isExist) {
                Box(
                    modifier = Modifier.fillMaxHeight().width(60.dp)
                        .background(MaxiPulsTheme.colors.uiKit.primary)
                        .clickableBlank {
                            onAdd()
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.back_ic),
                        tint = MaxiPulsTheme.colors.uiKit.white,
                        modifier = Modifier.size(36.dp).rotate(180f),
                        contentDescription = null
                    )
                }
            }
        }
    }

}

@Composable
private fun SportsmanTrainingCard(
    modifier: Modifier = Modifier,
    number: Int,
    name: String,
    lastname: String,
    middleName: String,
    age: Int,
    heartRateMax: Int,
    avatar: String,
    isSelect: Boolean,
    status: RemoteTrainingSportsmanStatus,
    onClick: () -> Unit,
) {
    val textColor =
        if (isSelect) MaxiPulsTheme.colors.uiKit.white else MaxiPulsTheme.colors.uiKit.textColor
    Column(
        modifier.background(
            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
        }.animateContentSize()
    ) {
        Spacer(modifier = Modifier.size((10).dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.background(
                        MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                        shape = CircleShape
                    ).clip(CircleShape).size(40.0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatar.isBlank()) {
                        Image(
                            painter = painterResource(Res.drawable.profile),
                            modifier = Modifier.size(
                                width = 16.dp,
                                21.dp
                            ),
                            colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                            contentDescription = null
                        )
                    } else {
                        MaxiImage(
                            modifier = Modifier.fillMaxSize(),
                            url = avatar,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))

                Column(modifier = Modifier) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = number.toString(), style = MaxiPulsTheme.typography.bold.copy(
                                color = textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "${lastname}. ${
                                name.firstOrNull()?.toString().orEmpty()
                            }. ${middleName.firstOrNull()?.toString().orEmpty()}.",
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = textColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${stringResource(Res.string.age)}: ${
                                stringResource(
                                    Res.string.age_text,
                                    age
                                )
                            }",
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = textColor,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Text(
                text = stringResource(status.title),
                style = MaxiPulsTheme.typography.semiBold.copy(
                    color = status.color,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.size(10.dp))

    }

}