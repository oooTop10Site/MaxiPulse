@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.project.theme.uiKit

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.MaxiPulsTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import kotlin.math.min

@Composable
fun MaxiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorIndicatorColor = MaxiPulsTheme.colors.uiKit.textFieldStroke,
        focusedIndicatorColor = MaxiPulsTheme.colors.uiKit.textFieldStroke,
    disabledIndicatorColor = MaxiPulsTheme.colors.uiKit.textFieldStroke,
    unfocusedIndicatorColor = MaxiPulsTheme.colors.uiKit.textFieldStroke,
    )
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = {
            if (placeholder == null) null else Text(
                text = placeholder,
                color = MaxiPulsTheme.colors.uiKit.placeholder
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Composable
fun MaxiOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Dp = 15.dp,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color.Transparent,
        errorBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent
    )
) {
    val focusManager = LocalFocusManager.current
//    val isFocused by interactionSource.collectIsFocusedAsState()
    var isFocused by remember { mutableStateOf(false) }
    LaunchedEffect(isFocused) {
        println("isFocused - $isFocused")
    }
    Box(
        modifier
    ) {
        BasicTextField(
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            textStyle = MaxiPulsTheme.typography.regular.copy(color = MaxiPulsTheme.colors.uiKit.textColor, fontSize = 14.sp),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth()
                .border(width = 1.dp, color = MaxiPulsTheme.colors.uiKit.textFieldStroke, shape = RoundedCornerShape(15.dp))
                .onFocusChanged { isFocused = it.isFocused },
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardActions = keyboardActions,
//            cursorBrush = SolidColor(currentIndicatorColor),
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    placeholder = {
                        Text(
                            text = placeholder.orEmpty(),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp
                            )
                        )
                    },
                    trailingIcon = trailingIcon,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        if (readOnly) focusManager.clearFocus(true)
                                        onClick?.invoke()
                                    }
                                }
                            }
                        },
                    isError = isError,
                    leadingIcon = leadingIcon,
                    colors = colors,
                    contentPadding = PaddingValues(
                        horizontal = 15.dp,
                        vertical = 6.dp
                    ),
                )
            }
        )

    }
}