package com.blackhand.trainingapp.domain.model.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserListData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val _id: String,
    @SerializedName("age")
    val age: String? = null,
    @SerializedName("__v")
    val v: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null
)