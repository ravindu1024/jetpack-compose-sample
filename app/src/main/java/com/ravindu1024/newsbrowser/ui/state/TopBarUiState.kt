package com.ravindu1024.newsbrowser.ui.state

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarUiState(
    val title: String = "",
    val actions: List<TopBarAction> = emptyList()
)

data class TopBarAction(
    val icon: ImageVector,
    val onClick: () -> Unit
)