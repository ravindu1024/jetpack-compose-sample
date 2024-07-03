package com.ravindu1024.newsbrowser.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object Dimensions {

    val marginSmall: Dp
    @Composable
    get() = 8.dp

    val marginNormal: Dp
        @Composable
        get() = 16.dp

    val marginLarge: Dp
        @Composable
        get() = 24.dp

    val marginExtraLarge: Dp
        @Composable
        get() = 32.dp


    val cornerRadiusNormal: Dp
        @Composable
        get() = 6.dp


    val progressIndicatorSize: Dp
        @Composable
        get() = 26.dp
}