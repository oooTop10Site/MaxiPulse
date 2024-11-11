package org.example.project.theme

import androidx.compose.runtime.compositionLocalOf

interface MaxiPulsColors {
    val uiKit: UIKitColors
}

class MaxiPulsColorsLight(
    override val uiKit: UIKitColors,


    ) : MaxiPulsColors


internal val LocalColors = compositionLocalOf<MaxiPulsColors> {
    error(
        "No colors provided! Make sure to wrap all usages of components in a " +
                "TalkTheme."
    )
}


fun lightColors(): MaxiPulsColors {
    return MaxiPulsColorsLight(
        uiKit = uiKitColors()
    )
}


fun darkColors(): MaxiPulsColors {
    return MaxiPulsColorsLight(
        uiKit = uiKitColors()
    )
}

