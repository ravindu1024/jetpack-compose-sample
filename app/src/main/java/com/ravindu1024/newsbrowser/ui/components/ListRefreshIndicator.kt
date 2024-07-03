package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ravindu1024.newsbrowser.ui.theme.Dimensions
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme

@Composable
fun ListRefreshIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(Dimensions.progressIndicatorSize)
            .padding(top = Dimensions.marginExtraLarge)
            .then(modifier)
    )
}

@Preview
@Composable
fun ListRefreshIndicatorPreview() {
    NewsBrowserTheme {
        ListRefreshIndicator(modifier = Modifier)
    }
}