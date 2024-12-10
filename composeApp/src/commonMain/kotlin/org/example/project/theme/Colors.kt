package org.example.project.theme

import androidx.compose.ui.graphics.Color

class UIKitColors(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val dropDown: Color,
    val buttonContainer: Color,
    val checkBoxContainer: Color,
    val linkedText: Color,
    val textFieldStroke: Color,
    val placeholder: Color,
    val textColor: Color,
    val lightTextColor: Color,
    val buttonContent: Color,
    val checkmark: Color,
    val modalSheet: Color,
    val divider: Color,
    val card: Color,
    val sportsmanAvatarBackground: Color,
    val textDropDown: Color,
    val disable: Color,

    val red500: Color,

    val blue500: Color,

    val grey500: Color,
    val grey800: Color,
    val grey400: Color,

    val green500: Color,

    val white: Color,

    val shimmerColor: List<Color>
)

fun uiKitColors() = UIKitColors(
    background = Color.White,
    buttonContainer = Color(0xFFE81F61),
    primary = Color(0xFFE81F61),
    secondary = Color(0xFFDF0B40),
    dropDown = Color(0xFFf56495),
    linkedText = Color(0xFFE81F61),
    checkBoxContainer = Color(0xFFE81F61),
    textFieldStroke = Color(0xFFA0A0A0),
    placeholder = Color(0xFFA0A0A0),
    textColor = Color.Black,
    lightTextColor = Color.White,
    buttonContent = Color.White,
    checkmark = Color.White,
    modalSheet = Color(0xFF1C2536),
    divider = Color(0xFFA0A0A0),
    card = Color(0xFFF0F0F0),
    sportsmanAvatarBackground = Color(0xFFD9D9D9),
    textDropDown = Color(0xFFC5C5C5),
    disable = Color(0xFF3093F9),

    red500 = Color(0xFFF48FB0),

    blue500 = Color(0xFF98C9FC),

    grey800 = Color(0xFF696969),
    grey500 = Color(0xFFDFDFDF),
    grey400 = Color(0xFFECECEC),

    green500 = Color(0xFF7ECA22),
    white = Color.White,

    shimmerColor = listOf(
        Color(0xFFB8B5B5),
        Color(0xFF8F8B8B),
        Color(0xFFB8B5B5)
    )

)

