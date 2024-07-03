package com.ravindu1024.newsbrowser.ui.components

import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    modifier: Modifier,
    url: String
){
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                android.webkit.WebView(it).apply {
                    settings.domStorageEnabled = true
                    settings.javaScriptEnabled = true

                    webChromeClient = WebChromeClient()

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = {
                if(url.startsWith("http://")) {
                    it.loadUrl(url.replace("http://", "https://"))
                }else{
                    it.loadUrl(url)
                }
            }
        )
    }
}