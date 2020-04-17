package com.example.googlenews.model


data class NewsItem(
    val titles: String,
    val contents: String,
    val dates: String,
    val userPhotos: Int

) {
    fun getTitle(): String? {
        return titles
    }

    fun getContent(): String? {
        return contents
    }

    fun getDate(): String? {
        return dates
    }

    fun getUserPhoto(): Int {
        return userPhotos
    }
}