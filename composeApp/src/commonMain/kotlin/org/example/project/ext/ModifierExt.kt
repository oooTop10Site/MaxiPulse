package org.example.project.ext

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import org.example.project.domain.model.ButtonActions

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun Modifier.clickableBlank(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onLongClick: (() -> Unit)? = null,
    buttonActions: ButtonActions = ButtonActions.Unlimit,
    onClick: () -> Unit,
): Modifier {
    var countClick by remember { mutableIntStateOf(0) }
    return combinedClickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = {
            if (buttonActions == ButtonActions.Unlimit || countClick < 1) {
                onClick()
            }
            println(countClick)
            countClick++
        },
        onLongClick = onLongClick
    )
}

//@Composable
//@OptIn(ExperimentalFoundationApi::class)
//internal fun Modifier.customClickable(
//    enabled: Boolean = true,
//    onClickLabel: String? = null,
//    role: Role? = null,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    onLongClick: (() -> Unit)? = null,
//    onClick: () -> Unit,
//): Modifier {
//    return combinedClickable(
//        interactionSource = interactionSource,
//        indication = rememberRipple(),
//        enabled = enabled,
//        onClickLabel = onClickLabel,
//        role = role,
//        onClick = onClick,
//        onLongClick = onLongClick
//    )
//}

internal fun Modifier.clickableRound(
    radius: Dp,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {
    return clip(RoundedCornerShape(radius))
        .clickable(enabled) {
            onClick()
        }
}

