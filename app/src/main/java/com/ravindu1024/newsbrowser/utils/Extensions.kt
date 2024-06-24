package com.ravindu1024.newsbrowser.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    this.launch(context = Dispatchers.IO, block = block)
}