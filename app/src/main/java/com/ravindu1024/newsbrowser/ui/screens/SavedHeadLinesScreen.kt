package com.ravindu1024.newsbrowser.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme

@Composable
fun SavedHeadLinesScreen(){
    Text(text = "Saved")
}

@Preview
@Composable
fun SavedHeadLinesPreview(){
    NewsBrowserTheme {
        SavedHeadLinesScreen()
    }
}