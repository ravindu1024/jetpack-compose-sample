package com.ravindu1024.data

import android.content.Context
import com.google.gson.Gson


class AppSharedPrefs constructor(
    context: Context
){
    private val sharedPrefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)
    private val gson = Gson()

}