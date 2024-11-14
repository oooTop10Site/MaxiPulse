package org.example.project.theme.uiKit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import org.example.project.theme.MaxiPulsTheme

@Composable
fun MaxiSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = MaxiPulsTheme.colors.uiKit.white,
        checkedTrackColor = MaxiPulsTheme.colors.uiKit.primary,
        checkedIconColor = MaxiPulsTheme.colors.uiKit.white
    ),
    interactionSource: MutableInteractionSource? = null,
) {
    Switch(checked, onCheckedChange, modifier, thumbContent, enabled, colors, interactionSource)
}