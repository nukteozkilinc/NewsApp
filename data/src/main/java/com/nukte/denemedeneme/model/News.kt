package com.nukte.denemedeneme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "newsTable")
@Serializable
data class News(
    @PrimaryKey @ColumnInfo(name = "id") var _id: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "excerpt") var excerpt: String,
    @ColumnInfo(name = "media") var media: String,
    @ColumnInfo(name = "published_date") var published_date: String,
    @ColumnInfo(name = "summary") var summary: String,
    var isSaved: Boolean,
    var link: String,
) : java.io.Serializable