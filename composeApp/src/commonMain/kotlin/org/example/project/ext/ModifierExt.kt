package org.example.project.ext

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ButtonActions
import org.example.project.theme.MaxiPulsTheme

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

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetx by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue =  2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(brush = Brush.linearGradient(
        colors = MaxiPulsTheme.colors.uiKit.shimmerColor,
        start = Offset(startOffsetx, 0f),
        end = Offset(startOffsetx + size.width.toFloat(), size.height.toFloat())
    ))
        .onGloballyPositioned {
            size = it.size
        }
}