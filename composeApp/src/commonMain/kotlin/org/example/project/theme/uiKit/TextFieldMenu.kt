package org.example.project.theme.uiKit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MaxiTextFieldResMenu(
    isError: Boolean = false,
    text: String,
    onChangeWorkScope: (T) -> Unit = {},
    expanden: Boolean,
    onExpanded: (Boolean) -> Unit,
    items: List<T>,
    itemToString: (T) -> StringResource,
    placeholderText: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanden,
        onExpandedChange = {
            if (enabled) {
                onExpanded(!expanden)
            }
        }) {
        MaxiOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryEditable)
                .height(Constants.TextFieldHeight),
            value = text,
            isError = isError,
            readOnly = true,
            onValueChange = {
            },
            singleLine = true,
            shape = 20.dp,
            trailingIcon = trailingIcon
                ?: {
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (expanden) 180f else 0f,
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
        ExposedDropdownMenu(
            containerColor = MaxiPulsTheme.colors.uiKit.dropDown,
            expanded = expanden && enabled,
            onDismissRequest = { onExpanded(false) }) {
            items.forEach { selectedItem ->
                DropdownMenuItem(text = {
                    Text(
                        text = stringResource(itemToString(selectedItem)),
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    )
                },
                    onClick = {
                        onChangeWorkScope(selectedItem); onExpanded(false)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MaxiTextFieldMenu(
    isError: Boolean = false,
    text: String,
    onChangeWorkScope: (T) -> Unit = {},
    expanden: Boolean,
    onExpanded: (Boolean) -> Unit,
    items: List<T>,
    itemToString: (T) -> String,
    placeholderText: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanden,
        onExpandedChange = {
            if (enabled) {
                onExpanded(!expanden)
            }
        }) {
        MaxiOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryEditable)
                .height(Constants.TextFieldHeight),
            value = text,
            isError = isError,
            readOnly = true,
            onValueChange = {
            },
            singleLine = true,
            shape = 20.dp,
            trailingIcon = trailingIcon
                ?: {
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (expanden) 180f else 0f,
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
        ExposedDropdownMenu(
            containerColor = MaxiPulsTheme.colors.uiKit.dropDown,
            expanded = expanden && enabled,
            onDismissRequest = { onExpanded(false) }) {
            items.forEach { selectedItem ->
                DropdownMenuItem(text = {
                    Text(
                        text = itemToString(selectedItem),
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    )
                },
                    onClick = {
                        onChangeWorkScope(selectedItem); onExpanded(false)
                    }
                )
            }
        }
    }
}