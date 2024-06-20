package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import com.ravindu1024.newsbrowser.ui.viewmodels.HeadlineViewModel


@Composable
fun HeadLineScreen(
    viewModel: HeadlineViewModel = hiltViewModel()
){

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Headlines")
    }
}

@Preview
@Composable
fun HeadLineScreenPreview(){
    NewsBrowserTheme {
        HeadLineScreen()
    }
}