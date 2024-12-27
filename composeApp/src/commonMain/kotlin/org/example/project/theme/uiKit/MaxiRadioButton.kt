package org.example.project.theme.uiKit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import org.example.project.theme.MaxiPulsTheme


@Composable
fun MaxiRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaxiPulsTheme.colors.uiKit.primary,
    ),
    interactionSource: MutableInteractionSource? = null,
    onClick: (() -> Unit)?,
    ) {
    RadioButton(
        selected = selected,
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        interactionSource = interactionSource
    )

}