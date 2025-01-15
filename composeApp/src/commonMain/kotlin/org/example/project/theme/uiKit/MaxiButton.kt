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
import androidx.compose.ui.text.style.TextOverflow
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
    buttonTextStyle: ButtonTextStyle = ButtonTextStyle.Bold,
) {
    val scope = rememberCoroutineScope()
    val styleText = when (buttonTextStyle) {
        ButtonTextStyle.Bold -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.SemiBold -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Medium -> {
            MaxiPulsTheme.typography.medium.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Regular -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Small -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.MobileBold -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileSemiBold -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileMedium -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileRegular -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileSmall -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
    Button(
        {
            onClick()
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
            style = styleText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

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
    buttonTextStyle: ButtonTextStyle = ButtonTextStyle.Bold,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val styleText = when (buttonTextStyle) {
        ButtonTextStyle.Bold -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.SemiBold -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Medium -> {
            MaxiPulsTheme.typography.medium.copy(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Regular -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.Small -> {
            MaxiPulsTheme.typography.bold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        ButtonTextStyle.MobileBold -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileSemiBold -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileMedium -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileRegular -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )

        ButtonTextStyle.MobileSmall -> MaxiPulsTheme.typography.bold.copy(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
    Button(
        {
            onClick()

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
        content()
    }
}


enum class ButtonTextStyle {
    Bold,
    SemiBold,
    Medium,
    Regular,
    Small,

    MobileBold,
    MobileSemiBold,
    MobileMedium,
    MobileRegular,
    MobileSmall,
}