package com.ravindu1024.newsbrowser.domain

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import javax.inject.Inject

private const val PREF_SOURCES = "sources"
private const val PREF_SAVED = "saved"

class AppSharedPrefs @Inject constructor(
    context: Context
){
    private val sharedPrefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)
    private val gson = Gson()

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

    fun saveHeadlines(headlines: List<NewsHeadline>){
        sharedPrefs
            .edit()
            .putString(PREF_SAVED, gson.toJson(headlines))
            .apply()
    }

    fun getSavedHeadlines(): List<NewsHeadline>{

        val savedJson = sharedPrefs.getString(PREF_SAVED, "[]")
        return gson.fromJson(savedJson, object: TypeToken<List<NewsHeadline>>() {}.type)
    }
}