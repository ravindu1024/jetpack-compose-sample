package com.ravindu1024.newsbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.ravindu1024.newsbrowser.ui.NewsApp
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsBrowserTheme {
                Surface {
                    NewsApp()
                }
            }
        }
    }
}
