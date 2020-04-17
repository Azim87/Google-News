package com.example.googlenews.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.googlenews.data.local.converter.NewsConverter
import com.google.gson.annotations.SerializedName


@Entity()
@TypeConverters(NewsConverter::class)
data class GoogleNewsResponse (

	@SerializedName("status")
	val status : String,

	@PrimaryKey(autoGenerate = true)
	@SerializedName("totalResults")
	val totalResults : Int,

	@SerializedName("articles")
	val articles : List<Articles>
)



