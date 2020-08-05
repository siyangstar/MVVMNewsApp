package com.androiddevs.mvvmnewsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    val author: String,
    val content: String?,
    val description: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    val source: Source,
    val title: String,
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String
) : Serializable