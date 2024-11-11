package org.example.project.platform

import androidx.compose.ui.text.font.FontFamily
import org.example.project.theme.typography.BaseTypography
import org.example.project.theme.typography.TextStyleBold
import org.example.project.theme.typography.TextStyleMedium
import org.example.project.theme.typography.TextStyleRegular
import org.example.project.theme.typography.TextStyleSemiBold

actual object MaxiPulsAppFonts {
    actual val regular: BaseTypography = TextStyleRegular(FontFamily.Default)
    actual val medium: BaseTypography = TextStyleMedium(FontFamily.Default)
    actual val semiBold: BaseTypography = TextStyleSemiBold(FontFamily.Default)
    actual val bold: BaseTypography = TextStyleBold(FontFamily.Default)

}