package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListRefreshIndicator(modifier: Modifier){
    CircularProgressIndicator(
        modifier = Modifier
            .size(28.dp)
            .padding(top = 32.dp)
            .then(modifier)
    )
}