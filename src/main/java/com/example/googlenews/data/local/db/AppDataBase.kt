package com.example.googlenews.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googlenews.data.local.dao.GoogleNewsDao
import com.example.googlenews.model.GoogleNewsResponse

@Database(
    entities = [GoogleNewsResponse::class],
    version = 3,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): GoogleNewsDao?
}