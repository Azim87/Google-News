package com.example.googlenews.data.api

import com.example.googlenews.model.GoogleNewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleClientService {
    @GET("v2/top-headlines/")
    fun getGoogleNewsAsync(
        @Query("sources") sources: String
    ): Deferred<GoogleNewsResponse>
}