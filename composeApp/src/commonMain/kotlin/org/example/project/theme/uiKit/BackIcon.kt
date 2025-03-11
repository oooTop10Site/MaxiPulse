package org.example.project.theme.uiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.painterResource


@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    isDebounce: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaxiPulsTheme.colors.uiKit.primary, shape = CircleShape)
            .clip(CircleShape)
            .clickableBlank(role = Role.Button, onClick = if (isDebounce) debouncedClick() {
                onClick()
            } else {
                { onClick() }
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(Res.drawable.back_ic),
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = MaxiPulsTheme.colors.uiKit.lightTextColor
        )
    }
}

@Composable
fun NextIcon(
    modifier: Modifier = Modifier, iconSize: Dp = 24.dp, isDebounce: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaxiPulsTheme.colors.uiKit.primary, shape = CircleShape)
            .clip(CircleShape)
            .clickableBlank(role = Role.Button, onClick = if (isDebounce) debouncedClick() {
                onClick()
            } else {
                { onClick() }
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(Res.drawable.back_ic),
            contentDescription = null,
            modifier = Modifier.size(iconSize).rotate(180f),
            tint = MaxiPulsTheme.colors.uiKit.lightTextColor
        )
    }
}