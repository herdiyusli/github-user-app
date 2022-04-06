package com.herdi.yusli.githubappherdiyusli.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_favorite")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "img_url")
    var img_url: String
) : Parcelable


