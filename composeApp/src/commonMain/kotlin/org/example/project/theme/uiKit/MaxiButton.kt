package org.example.project.theme.uiKit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.domain.model.ButtonActions
import org.example.project.theme.MaxiPulsTheme


@Composable
fun MaxiButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaxiPulsTheme.colors.uiKit.buttonContainer,
        contentColor = MaxiPulsTheme.colors.uiKit.buttonContent
    ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    text: String,
    buttonActions: ButtonActions = ButtonActions.Unlimit
) {
    val scope = rememberCoroutineScope()
    Button(
        {
            if (buttonActions == ButtonActions.Unlimit) {
                onClick()
            } else {
                scope.launch() {
                    onClick()
                    delay(200L)
                }
            }
        },
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
    ) {
        Text(
            text,
            style = MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}