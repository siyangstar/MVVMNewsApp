package com.androiddevs.mvvmnewsapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String
)