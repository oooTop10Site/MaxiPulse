package org.example.project.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp


internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }


@Composable
internal fun MaxiPulsTheme(
    typography: MaxiPulsTypography = textStyles(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides if (darkTheme) darkColors() else lightColors(),
        LocalTypography provides typography,
        LocalViewConfiguration provides LocalViewConfiguration.current.updateViewConfiguration()
    ) {
        content()
    }
}


object MaxiPulsTheme {

    val colors: MaxiPulsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: MaxiPulsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

}


fun ViewConfiguration.updateViewConfiguration() = object : ViewConfiguration {
    override val longPressTimeoutMillis
        get() = this@updateViewConfiguration.longPressTimeoutMillis

    override val doubleTapTimeoutMillis
        get() = this@updateViewConfiguration.doubleTapTimeoutMillis

    override val doubleTapMinTimeMillis
        get() = this@updateViewConfiguration.doubleTapMinTimeMillis

    override val touchSlop: Float
        get() = this@updateViewConfiguration.touchSlop
    override val minimumTouchTargetSize: DpSize
        get() = DpSize(40.dp, 40.dp)
}