package org.example.project.theme


import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import org.example.project.theme.typography.BaseTypography
import org.example.project.platform.MaxiPulsAppFonts


class MaxiPulsTypography(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
)


@Composable
fun textStyles(): MaxiPulsTypography {
    return MaxiPulsTypography(
        regular = MaxiPulsAppFonts.regular.getComposeTextStyle(),
        medium = MaxiPulsAppFonts.medium.getComposeTextStyle(),
        semiBold = MaxiPulsAppFonts.semiBold.getComposeTextStyle(),
        bold = MaxiPulsAppFonts.bold.getComposeTextStyle(),
    )
}

private fun toTextStyle(typographyStyle: BaseTypography): TextStyle {
    return TextStyle(
        fontSize = typographyStyle.fontSize,
        fontFamily = typographyStyle.fontFamily,
        lineHeight = typographyStyle.lineHeight,
        fontWeight = typographyStyle.fontWeight,
        platformStyle = PlatformTextStyle(
            PlatformSpanStyle.Default,
            PlatformParagraphStyle(),
        ),
        baselineShift = BaselineShift(typographyStyle.baselineShift),
        lineHeightStyle = LineHeightStyle(
            LineHeightStyle.Alignment.Center,
            LineHeightStyle.Trim.None
        )
    )
}

fun BaseTypography.getComposeTextStyle(): TextStyle {
    return toTextStyle(this)
}

internal val LocalTypography = compositionLocalOf<MaxiPulsTypography> {
    error(
        "No typography provided! Make sure to wrap all usages of components in a " +
                "TalkTheme."
    )
}
