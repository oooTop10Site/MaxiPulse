package org.example.project.theme.typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class TextStyleRegular(override val fontFamily: FontFamily) : BaseTypography {


    override val fontSize: TextUnit = 24.sp
    override val fontWeight: FontWeight = FontWeight.W400
    override val lineHeight: TextUnit
        get() = fontSize

}