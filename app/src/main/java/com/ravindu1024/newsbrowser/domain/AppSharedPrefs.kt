package com.ravindu1024.newsbrowser.domain

import android.content.Context
import javax.inject.Inject

private const val PREF_SOURCES = "sources"

class AppSharedPrefs @Inject constructor(
    context: Context
){
    private val sharedPrefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

    fun saveSources(sources: List<String>){
        if(sources.isEmpty()){
            sharedPrefs.edit().remove(PREF_SOURCES).apply()
        }else {
            sharedPrefs
                .edit()
                .putStringSet(PREF_SOURCES, sources.toSet())
                .apply()
        }
    }

    fun getSources(): List<String>{
        return sharedPrefs.getStringSet(PREF_SOURCES, emptySet())?.toList() ?: emptyList()
    }
}