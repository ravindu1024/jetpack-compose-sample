package com.ravindu1024.data

import android.content.Context
import com.google.gson.Gson


class AppSharedPrefs(
    context: Context
) {
    private val sharedPrefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

}