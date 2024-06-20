package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme


@Composable
fun TopBar(
    canNavigateBack: Boolean,
    title: String?,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title ?: "") },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(),
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
fun TopBarPreview(){
    NewsBrowserTheme {
        TopBar(canNavigateBack = true, title = "Title", navigateUp = { })
    }
}