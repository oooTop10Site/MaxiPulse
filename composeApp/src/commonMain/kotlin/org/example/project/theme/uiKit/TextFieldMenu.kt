package org.example.project.theme.uiKit

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.drop_ic
import org.example.project.theme.MaxiPulsTheme
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.example.project.ext.clickableBlank


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MaxiTextFieldResMenu(
    isError: Boolean = false,
    text: String,
    currentValue: T,
    onChangeWorkScope: (T) -> Unit = {},
    items: List<T>,
    itemToString: (T) -> StringResource,
    placeholderText: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    var isExpand by remember { mutableStateOf(false) }
    var width by remember { mutableStateOf(0.dp) }
    val bottomPadding by animateDpAsState(
        targetValue = if (isExpand) 0.dp else 20.dp,
        animationSpec = tween(durationMillis = 150) // Длительность анимации в миллисекундах
    )
    BoxWithConstraints(modifier = modifier) {
        width = maxWidth
        Column(
        ) {
            MaxiOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Constants.TextFieldHeight),
                value = text,
                enabled = enabled,
                isError = isError,
                readOnly = true,
                onClick = {
                    if(enabled) {
                        isExpand = !isExpand
                    }
                },
                onValueChange = {
                },
                singleLine = true,
                shape = if (isExpand) RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = bottomPadding,
                    bottomEnd = bottomPadding
                ) else RoundedCornerShape(20.dp),
                trailingIcon = trailingIcon
                    ?: {
                        val rotationAngle by animateFloatAsState(
                            targetValue = if (isExpand) 180f else 0f,
                            animationSpec = tween(durationMillis = 300) // Длительность анимации в миллисекундах
                        )

                        Icon(
                            painter = painterResource(Res.drawable.drop_ic),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(rotationAngle),
                            tint = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    },
                placeholder = placeholderText,
            )
            Box(modifier = Modifier.width(width).offset(x = 0.dp, y = (-1).dp)) {
                if (isExpand) {
                    Popup(
                        onDismissRequest = {
                            isExpand = !isExpand
                        },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Column(
                            modifier = Modifier.width(width)
                                .heightIn(max = 163.dp)
                                .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                                .background(
                                    color = MaxiPulsTheme.colors.uiKit.white,
                                    shape = RoundedCornerShape(
                                        bottomStart = 15.dp,
                                        bottomEnd = 15.dp
                                    )
                                )
                                .animateContentSize().border(
                                    shape = RoundedCornerShape(
                                        bottomStart = 15.dp,
                                        bottomEnd = 15.dp
                                    ),
                                    color = MaxiPulsTheme.colors.uiKit.textFieldStroke,
                                    width = 1.dp
                                ),
                        ) {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                items.forEachIndexed { index, item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickableBlank {
                                            onChangeWorkScope(item)
                                            isExpand = !isExpand
                                        },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(itemToString(item)),
                                            style = MaxiPulsTheme.typography.medium.copy(
                                                color = if (currentValue == item) MaxiPulsTheme.colors.uiKit.textColor else MaxiPulsTheme.colors.uiKit.placeholder,
                                                fontSize = 14.sp,
                                                lineHeight = 14.sp
                                            ),
                                            modifier = Modifier.padding(
                                                top = 10.dp,
                                                bottom = 10.dp,
                                                start = 16.dp,
                                                end = 16.dp
                                            )
                                        )
                                    }

                                    if (index != items.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaxiPulsTheme.colors.uiKit.grey500
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MaxiTextFieldMenu(
    isError: Boolean = false,
    text: String,
    currentValue: T,
    onChangeWorkScope: (T) -> Unit = {},
    items: List<T>,
    itemToString: (T) -> String,
    placeholderText: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    var isExpand by remember { mutableStateOf(false) }
    var width by remember { mutableStateOf(0.dp) }
    val bottomPadding by animateDpAsState(
        targetValue = if (isExpand) 0.dp else 20.dp,
        animationSpec = tween(durationMillis = 150) // Длительность анимации в миллисекундах
    )
    BoxWithConstraints(modifier = modifier) {
        width = maxWidth
        Column(
        ) {
            MaxiOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Constants.TextFieldHeight),
                value = text,
                isError = isError,
                readOnly = true,
                onClick = {
                    if (!isExpand) {
                        isExpand = !isExpand
                    }
                },
                onValueChange = {
                },
                singleLine = true,
                shape = if (isExpand) RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = bottomPadding,
                    bottomEnd = bottomPadding
                ) else RoundedCornerShape(20.dp),
                trailingIcon = trailingIcon
                    ?: {
                        val rotationAngle by animateFloatAsState(
                            targetValue = if (isExpand) 180f else 0f,
                            animationSpec = tween(durationMillis = 300) // Длительность анимации в миллисекундах
                        )

                        Icon(
                            painter = painterResource(Res.drawable.drop_ic),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(rotationAngle),
                            tint = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    },
                placeholder = placeholderText,
            )
            Box(modifier = Modifier.width(width).offset(x = 0.dp, y = (-1).dp)) {
                if (isExpand) {
                    Popup(
                        onDismissRequest = {
                            isExpand = !isExpand
                        },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Column(
                            modifier = Modifier.width(width)
                                .heightIn(max = 163.dp)
                                .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                                .background(
                                    color = MaxiPulsTheme.colors.uiKit.white,
                                    shape = RoundedCornerShape(
                                        bottomStart = 15.dp,
                                        bottomEnd = 15.dp
                                    )
                                )
                                .animateContentSize().border(
                                    shape = RoundedCornerShape(
                                        bottomStart = 15.dp,
                                        bottomEnd = 15.dp
                                    ),
                                    color = MaxiPulsTheme.colors.uiKit.textFieldStroke,
                                    width = 1.dp
                                ),
                        ) {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                items.forEachIndexed { index, item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickableBlank {
                                            onChangeWorkScope(item)
                                            isExpand = !isExpand
                                        },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = itemToString(item),
                                            style = MaxiPulsTheme.typography.medium.copy(
                                                color = if (currentValue == item) MaxiPulsTheme.colors.uiKit.textColor else MaxiPulsTheme.colors.uiKit.placeholder,
                                                fontSize = 14.sp,
                                                lineHeight = 14.sp
                                            ),
                                            modifier = Modifier.padding(
                                                top = 10.dp,
                                                bottom = 10.dp,
                                                start = 16.dp,
                                                end = 16.dp
                                            )
                                        )
                                    }

                                    if (index != items.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaxiPulsTheme.colors.uiKit.grey500
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}