package com.blackhand.trainingapp.domain.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserRemoteKey")
data class UserRemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    var prevPage: Int?,
    var nextPage: Int?
)
