package com.blackhand.trainingapp.domain.model

import com.google.gson.annotations.SerializedName

data class UserListData(
    @SerializedName("__v")
    val v: Int? = null,
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("age")
    val age: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null
)