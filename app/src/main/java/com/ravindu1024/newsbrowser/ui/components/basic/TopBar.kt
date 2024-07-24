package com.ravindu1024.newsbrowser.ui.components.basic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme

@Composable
fun TopBar(
    canNavigateBack: Boolean,
    uiState: TopBarUiState,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = uiState.title) },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(),
        actions = {
            uiState.actions.forEach { action ->
                IconButton(onClick = { action.onClick() }, modifier = Modifier.testTag(action.icon.name)) {
                    Icon(action.icon, null)
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    val actions = listOf(
        TopBarAction(
            icon = Icons.Default.Favorite,
            onClick = {}
        )
    )
    val uiState = TopBarUiState("Title", actions = actions)
    NewsBrowserTheme {
        TopBar(canNavigateBack = true, uiState = uiState, navigateUp = { })
    }
}

data class TopBarUiState(
    val title: String = "",
    val actions: List<TopBarAction> = emptyList()
)

data class TopBarAction(
    val icon: ImageVector,
    val onClick: () -> Unit
)