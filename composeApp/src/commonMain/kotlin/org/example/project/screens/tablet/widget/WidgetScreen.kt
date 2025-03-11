package org.example.project.screens.tablet.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.purple_brush
import maxipuls.composeapp.generated.resources.select_all
import maxipuls.composeapp.generated.resources.settings_ic
import maxipuls.composeapp.generated.resources.start_tarining
import org.example.project.domain.model.widget.WidgetSize
import org.example.project.domain.model.widget.WidgetUI
import org.example.project.domain.model.widget.WidgetUIEvent
import org.example.project.ext.clickableBlank
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.tablet.miniPulseWidget.MiniPulseWidgetScreen
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiRoundCheckBox
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.stringResource

class WidgetScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            WidgetViewModel()
        }
        val dragAndDropState = rememberDragAndDropState<WidgetUI>()
        val navigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer() {
            if (state.isEditing) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = MaxiPulsTheme.colors.uiKit.textColor.copy(alpha = 0.2f))
                        .blur(50.dp)
                )
            }
            DragAndDropContainer(
                state = dragAndDropState,
            ) {
                if (state.isEditing) {
                    Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                        MaxiButton(
                            onClick = {},
                            modifier = Modifier.width(416.dp).height(69.dp).blur(50.dp),
                            text = stringResource(Res.string.start_tarining),
                            buttonTextStyle = ButtonTextStyle.Bold
                        )
                        Spacer(Modifier.size(40.dp))
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.isEditing) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        MaxiPulsTheme.colors.uiKit.primary,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape).clickableBlank {
                                        viewModel.changeIsEditing()
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
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.select_all),
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                MaxiRoundCheckBox(
                                    isChecked = state.isSelectAll,
                                    onValueChange = { viewModel.changeIsSelectAll() },
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.size(40.dp)
                                .background(
                                    MaxiPulsTheme.colors.uiKit.primary,
                                    shape = CircleShape
                                ).clickableBlank { viewModel.changeIsEditing() }
                                .clip(CircleShape).align(Alignment.End),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.settings_ic),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(
                        modifier = Modifier.padding(bottom = 20.dp).weight(1f).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        val widgets = state.widgets.subList(0, 3)
                        widgets.forEach {
                            DraggableItem(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                state = dragAndDropState,
                                enabled = !state.isEditing,
                                key = it.id, // Unique key for each draggable item
                                data = it, // Data to be passed to the drop target,
                                draggableContent = {
                                    WidgetItem(
                                        modifier = Modifier.fillMaxSize().alpha(0.6f),
                                        title = stringResource(it.title),
                                        icon = it.icon,
                                        size = WidgetSize.Small,
                                        isEditing = state.isEditing,
                                        isSelect = it in state.selected,
                                        changeEdit = { viewModel.changeSelect(it) }
                                    ) {

                                    }
                                }
                            ) {
                                WidgetItem(
                                    modifier = Modifier.fillMaxSize().dropTarget(
                                        state = dragAndDropState,
                                        key = it.id, // Unique key for each drop target
                                        onDrop = { state -> // Data passed from the draggable item
                                            viewModel.changePosition(
                                                moving = state.data,
                                                stating = it
                                            )
                                        }
                                    ),
                                    isEditing = state.isEditing,
                                    changeEdit = { viewModel.changeSelect(it) },
                                    title = stringResource(it.title),
                                    icon = it.icon,
                                    size = WidgetSize.Small,
                                    isSelect = it in state.selected,
                                    onClick = debouncedClick {
                                        when (it.event) {
                                            WidgetUIEvent.AppMiniPulse -> navigator.push(
                                                MiniPulseWidgetScreen()
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.weight(2f).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            val widgets = state.widgets.subList(3, 5)
                            widgets.forEach {
                                DraggableItem(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    state = dragAndDropState,
                                    enabled = !state.isEditing,
                                    key = it.id, // Unique key for each draggable item
                                    data = it, // Data to be passed to the drop target,
                                    draggableContent = {
                                        WidgetItem(
                                            modifier = Modifier.fillMaxSize().alpha(0.6f),
                                            title = stringResource(it.title),
                                            icon = it.icon,
                                            showElevation = false,
                                            size = WidgetSize.Small,
                                            isEditing = state.isEditing,
                                            isSelect = it in state.selected,
                                            changeEdit = { viewModel.changeSelect(it) }
                                        ) {

                                        }
                                    }
                                ) {
                                    WidgetItem(
                                        modifier = Modifier.fillMaxSize().dropTarget(
                                            state = dragAndDropState,
                                            key = it.id, // Unique key for each drop target
                                            onDrop = { state -> // Data passed from the draggable item
                                                viewModel.changePosition(
                                                    moving = state.data,
                                                    stating = it
                                                )
                                            },
                                        ),
                                        title = stringResource(it.title),
                                        icon = it.icon,
                                        size = WidgetSize.Small,
                                        isEditing = state.isEditing,
                                        isSelect = it in state.selected,
                                        changeEdit = { viewModel.changeSelect(it) }
                                    ) {

                                    }
                                }
                            }
                        }
                        Spacer(Modifier.size(20.dp))
                        state.widgets.lastOrNull()?.let {
                            DraggableItem(
                                modifier = Modifier.weight(2f).fillMaxHeight(),
                                state = dragAndDropState,
                                enabled = !state.isEditing,
                                key = it.id, // Unique key for each draggable item
                                data = it, // Data to be passed to the drop target,
                                draggableContent = {
                                    WidgetItem(
                                        modifier = Modifier.fillMaxSize().alpha(0.6f),
                                        title = stringResource(it.title),
                                        icon = it.icon,
                                        showElevation = false,
                                        size = WidgetSize.Large,
                                        isEditing = state.isEditing,
                                        isSelect = it in state.selected,
                                        changeEdit = { viewModel.changeSelect(it) }
                                    ) {

                                    }
                                }
                            ) {
                                WidgetItem(
                                    modifier = Modifier.fillMaxSize().dropTarget(
                                        state = dragAndDropState,
                                        key = it.id, // Unique key for each drop target
                                        onDrop = { state -> // Data passed from the draggable item
                                            viewModel.changePosition(
                                                moving = state.data,
                                                stating = it
                                            )
                                        }
                                    ),
                                    title = stringResource(it.title),
                                    icon = it.icon,
                                    size = WidgetSize.Large,
                                    isEditing = state.isEditing,
                                    isSelect = it in state.selected,
                                    changeEdit = { viewModel.changeSelect(it) }
                                ) {

                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(20.dp))
                    if (!state.isEditing) {
                        MaxiButton(
                            onClick = {},
                            modifier = Modifier.width(416.dp).height(69.dp),
                            text = stringResource(Res.string.start_tarining),
                            buttonTextStyle = ButtonTextStyle.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WidgetItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: DrawableResource,
    size: WidgetSize,
    changeEdit: () -> Unit,
    isEditing: Boolean,
    isSelect: Boolean,
    showElevation: Boolean = true,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
            if (isEditing) changeEdit() else onClick()
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = if (showElevation) CardDefaults.cardElevation(
            defaultElevation = 7.dp,
            hoveredElevation = 2.dp
        ) else CardDefaults.cardElevation(
            0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            UglyGradientBackground(modifier = Modifier.fillMaxSize())
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val sizeText = (16 + (8 * size.inch)).sp
                Text(
                    text = title, style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = sizeText,
                        lineHeight = sizeText,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                        textAlign = TextAlign.Center,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(10.dp))
                Image(
                    painter = painterResource(icon),
                    modifier = Modifier.height(100.dp + 125.dp * size.inch),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )

            }
            if (isEditing) {
                MaxiRoundCheckBox(
                    isChecked = isSelect,
                    onValueChange = { changeEdit() },
                    modifier = Modifier.padding(10.dp).size(30.dp).align(
                        Alignment.TopEnd
                    )
                )
            }
        }
    }
}

@Composable
fun UglyGradientBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Первый градиент
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFA93A), // первый цвет
                    Color(0xFFE81F61), // второй цвет
                    Color(0xFF003093F9)  // третий цвет
                ),
                center = Offset(width * -0.25f, height * -0.2f), // Соотношение для адаптации
                radius = width * 1.5f // Радиус градиента также адаптируется
            ),
            size = size
        )

        // Второй градиент
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFA93A), // первый цвет
                    Color(0xFFE81F61), // второй цвет
                    Color(0xFF003093F9)  // третий цвет
                ),
                center = Offset(width * 0.8f, height * 0.5f), // Соотношение для адаптации
                radius = width * 0.6f // Радиус также пропорционален
            ),
            size = size
        )

        // Белый цвет с альфа-каналом
        drawRect(
            color = Color.White.copy(alpha = 0.2f), // белый цвет с альфа-каналом
            size = size
        )
    }
}

@Composable
fun PurpleGradientBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Линейный градиент
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFA93A), // первый цвет
                    Color(0xFFE81F61), // второй цвет
                    Color(0xFF3093F9)  // третий цвет
                ),
                start = Offset(x = -0.38f * width, y = -0.37f * height), // Координаты адаптивны
                end = Offset(x = 1.75f * width, y = 0.45f * height) // Координаты адаптивны
            ),
            size = size
        )

        // Белый полупрозрачный слой
        drawRect(
            color = Color.White.copy(alpha = 0.3f), // Белый цвет с альфа-каналом
            size = size
        )
    }
}

