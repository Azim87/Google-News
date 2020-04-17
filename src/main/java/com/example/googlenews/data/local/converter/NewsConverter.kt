package com.example.googlenews.data.local.converter

import androidx.room.TypeConverter
import com.example.googlenews.model.Articles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(raw: String?): List<Articles?>? {
        if (raw == null) {
            return null
        }
        val type =
            object : TypeToken<List<Articles?>?>() {}.type
        return gson.fromJson<List<Articles?>>(raw, type)
    }

    @TypeConverter
    fun toList(news: List<Articles?>?): String? {
        return gson.toJson(news)
    }
}