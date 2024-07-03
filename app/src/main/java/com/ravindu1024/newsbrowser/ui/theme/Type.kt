package com.ravindu1024.newsbrowser.ui.theme

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ravindu1024.newsbrowser.ui.theme.CustomColors.primaryTextColor

object CustomTypography {

    val textRegular: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorScheme.primaryTextColor
        )

    val textLight: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = colorScheme.primaryTextColor
        )

    val textMediumBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorScheme.primaryTextColor
        )

    val textSmallLight: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = colorScheme.primaryTextColor
        )
}