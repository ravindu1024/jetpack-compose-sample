package com.ravindu1024.newsbrowser.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Gray100 = Color(0xFFF5F5F5)
val Gray300 = Color(0xFFE0E0E0)
val Gray900 = Color(0xFF212121)
val Gray800 = Color(0xFF424242)

val primaryTextLight = Color(0xE6000000)
val secondaryTextLight = Color(0xB4000000)
val tertiaryTextLight = Color(0x8B000000)

val primaryTextDark = Color(0xFFFFFFFF)
val secondaryTextDark = Color(0xB4FFFFFF)
val tertiaryTextDark = Color(0x8BFFFFFF)




object CustomColors{

    val ColorScheme.primaryTextColor: Color
        @Composable
        get() = if (isSystemInDarkTheme()) primaryTextDark else primaryTextLight

    val ColorScheme.secondaryTextColor: Color
        @Composable
        get() = if (isSystemInDarkTheme()) secondaryTextDark else secondaryTextLight

    val ColorScheme.tertiaryTextColor: Color
        @Composable
        get() = if (isSystemInDarkTheme()) tertiaryTextDark else tertiaryTextLight

    val ColorScheme.backgroundColor: Color
        @Composable
        get() = if (isSystemInDarkTheme()) Gray900 else Color.White

    val ColorScheme.newsCardColor: Color
        @Composable
        get() =
            if (isSystemInDarkTheme()) Gray800 else Gray300

}