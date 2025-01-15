package org.example.project.theme.uiKit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.MaxiPulsTheme


@Composable
fun MaxiOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(15.dp),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaxiPulsTheme.colors.uiKit.white,
        contentColor = MaxiPulsTheme.colors.uiKit.primary,
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = BorderStroke(1.dp, color = MaxiPulsTheme.colors.uiKit.primary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    text: String,
) {
    OutlinedButton(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
    ) {
        Text(text, style = MaxiPulsTheme.typography.bold.copy(fontSize = 20.sp))
    }
}