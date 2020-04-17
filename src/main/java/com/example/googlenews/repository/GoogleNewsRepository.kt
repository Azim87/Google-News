package com.example.googlenews.repository

import com.example.googlenews.model.GoogleNewsResponse
import com.example.googlenews.util.UseCaseResult

interface GoogleNewsRepository {
    suspend fun getGoogleNews(news: String): UseCaseResult<GoogleNewsResponse>
    suspend fun putShared(key: String, value: Boolean)
    fun getShared(key: String): Boolean
}