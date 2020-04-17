package com.example.googlenews.data.local.dao

import androidx.room.*
import com.example.googlenews.model.GoogleNewsResponse

@Dao
interface GoogleNewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(googleNews: GoogleNewsResponse)

    @Query("SELECT * FROM GoogleNewsResponse")
    suspend fun getAllNews(): GoogleNewsResponse

    @Update()
    suspend fun update(googleNews: GoogleNewsResponse)
}