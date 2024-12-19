package org.example.project.screens.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drag.DropStrategy
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.minipulse_app
import maxipuls.composeapp.generated.resources.mobile_widget
import maxipuls.composeapp.generated.resources.purple_brush
import maxipuls.composeapp.generated.resources.settings_ic
import maxipuls.composeapp.generated.resources.start_tarining
import org.example.project.domain.model.widget.WidgetSize
import org.example.project.domain.model.widget.WidgetUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.root.RootNavigator
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiButton
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
            DragAndDropContainer(
                state = dragAndDropState,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(40.dp)
                            .background(
                                MaxiPulsTheme.colors.uiKit.primary,
                                shape = CircleShape
                            )
                            .clip(CircleShape).align(Alignment.End),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(Res.drawable.settings_ic),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp).clickableBlank {

                            },
                            tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        var widgetUI1 by remember { mutableStateOf( WidgetUI((0..20000000).random())) }
                        var widgetUI2 by remember { mutableStateOf( WidgetUI((0..20000000).random())) }
                        var widgetUI3 by remember { mutableStateOf( WidgetUI((0..20000000).random())) }
                        DraggableItem(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            state = dragAndDropState,
                            key = widgetUI1.id, // Unique key for each draggable item
                            data = widgetUI1, // Data to be passed to the drop target,
                            draggableContent = {
                                WidgetItem(
                                    modifier = Modifier.fillMaxSize().alpha(0.6f),
                                    backgroundIcon = Res.drawable.purple_brush,
                                    title = widgetUI1.id.toString(),
                                    icon = Res.drawable.mobile_widget,
                                    size = WidgetSize.Small
                                ) {

                                }
                            }
                        ) {
                            WidgetItem(
                                modifier = Modifier.fillMaxSize().dropTarget(
                                    state = dragAndDropState,
                                    key = widgetUI1.id, // Unique key for each drop target
                                    onDrop = { state -> // Data passed from the draggable item
                                        widgetUI1 = state.data
                                    }
                                ),
                                backgroundIcon = Res.drawable.purple_brush,
                                title = widgetUI1.id.toString(),
                                icon = Res.drawable.mobile_widget,
                                size = WidgetSize.Small
                            ) {

                            }
                        }
                        Spacer(Modifier.size(20.dp))
                        DraggableItem(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            state = dragAndDropState,
                            key = widgetUI2.id, // Unique key for each draggable item
                            data = widgetUI2, // Data to be passed to the drop target
                            draggableContent = {
                                WidgetItem(
                                    modifier = Modifier.fillMaxSize().alpha(0.6f),
                                    backgroundIcon = Res.drawable.purple_brush,
                                    title = widgetUI2.id.toString(),
                                    icon = Res.drawable.mobile_widget,
                                    size = WidgetSize.Small
                                ) {

                                }
                            }
                        ) {
                            WidgetItem(
                                modifier = Modifier.fillMaxSize().dropTarget(
                                    state = dragAndDropState,
                                    key = widgetUI2.id, // Unique key for each drop target
                                    onDrop = { state -> // Data passed from the draggable item
                                        widgetUI2 = state.data
                                    }
                                ),
                                backgroundIcon = Res.drawable.purple_brush,
                                title = widgetUI2.id.toString(),
                                icon = Res.drawable.mobile_widget,
                                size = WidgetSize.Small
                            ) {

                            }
                        }
                        Spacer(Modifier.size(20.dp))
                        DraggableItem(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            state = dragAndDropState,
                            key = widgetUI3.id, // Unique key for each draggable item
                            data = widgetUI3, // Data to be passed to the drop target
                            draggableContent = {
                                WidgetItem(
                                    modifier = Modifier.fillMaxSize().alpha(0.6f),
                                    backgroundIcon = Res.drawable.purple_brush,
                                    title = widgetUI3.id.toString(),
                                    icon = Res.drawable.mobile_widget,
                                    size = WidgetSize.Small
                                ) {

                                }
                            }
                        ) {
                            WidgetItem(
                                modifier = Modifier.fillMaxSize().dropTarget(
                                    state = dragAndDropState,
                                    key = widgetUI3.id, // Unique key for each drop target
                                    onDrop = { state -> // Data passed from the draggable item
                                        widgetUI3 = state.data
                                    }
                                ),
                                backgroundIcon = Res.drawable.purple_brush,
                                title = widgetUI3.id.toString(),
                                icon = Res.drawable.mobile_widget,
                                size = WidgetSize.Small
                            ) {

                            }
                        }

                    }
                    Spacer(Modifier.size(20.dp))

                    Row(
                        modifier = Modifier.weight(2f).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            WidgetItem(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                backgroundIcon = Res.drawable.purple_brush,
                                title = stringResource(Res.string.minipulse_app),
                                icon = Res.drawable.mobile_widget,
                                size = WidgetSize.Small
                            ) {

                            }
                            Spacer(Modifier.size(20.dp))
                            WidgetItem(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                backgroundIcon = Res.drawable.purple_brush,
                                title = stringResource(Res.string.minipulse_app),
                                icon = Res.drawable.mobile_widget,
                                size = WidgetSize.Small
                            ) {

                            }
                        }
                        Spacer(Modifier.size(20.dp))
                        WidgetItem(
                            modifier = Modifier.weight(2f).fillMaxHeight(),
                            backgroundIcon = Res.drawable.purple_brush,
                            title = stringResource(Res.string.minipulse_app),
                            icon = Res.drawable.mobile_widget,
                            size = WidgetSize.Large
                        ) {

                        }
                    }
                    Spacer(Modifier.size(20.dp))
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

@Composable
fun WidgetItem(
    modifier: Modifier = Modifier,
    backgroundIcon: DrawableResource,
    title: String,
    icon: DrawableResource,
    size: WidgetSize,
    onClick: () -> Unit,
) {
    Box(
        modifier.clickableBlank { onClick() }.clip(RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(backgroundIcon),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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

            Image(
                painter = painterResource(icon),
                modifier = Modifier.height(100.dp + 125.dp * size.inch),
                contentScale = ContentScale.FillHeight,
                contentDescription = null
            )

        }
    }
}