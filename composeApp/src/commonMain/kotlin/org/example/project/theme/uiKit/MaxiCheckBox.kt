package org.example.project.theme.uiKit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import org.example.project.theme.MaxiPulsTheme

@Composable
fun MaxiCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaxiPulsTheme.colors.uiKit.checkBoxContainer,
        checkmarkColor = MaxiPulsTheme.colors.uiKit.checkmark
    ),
    interactionSource: MutableInteractionSource? = null
) {
    Checkbox(
        checked,
        onCheckedChange,
        modifier,
        enabled,
        colors,
        interactionSource
    )
}