package com.example.googlenews.repository

import com.example.googlenews.data.api.GoogleClientNewsBuilder
import com.example.googlenews.model.GoogleNewsResponse
import com.example.googlenews.util.PreferenceHelper
import com.example.googlenews.util.UseCaseResult

class GoogleNewsRepositoryImpl(
    private val googleClient: GoogleClientNewsBuilder,
    private val sharedStorage: PreferenceHelper
) : GoogleNewsRepository {
    var newsResponse: GoogleNewsResponse? = null

    override suspend fun getGoogleNews(news: String): UseCaseResult<GoogleNewsResponse> {
        return try {
            newsResponse = googleClient.buildRetrofit()
                .getGoogleNewsAsync(news)
                .await()
            UseCaseResult.Success(newsResponse!!)

        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    override suspend fun putShared(key: String, value: Boolean) {
        sharedStorage.setBooleanPreference(key, value)
    }

    override fun getShared(key: String): Boolean {
        return sharedStorage.getBooleanPreference(key)
    }
}