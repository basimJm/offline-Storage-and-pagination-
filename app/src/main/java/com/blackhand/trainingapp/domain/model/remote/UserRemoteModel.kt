package com.blackhand.trainingapp.domain.model.remote

import com.google.gson.annotations.SerializedName

data class UserRemoteModel(
    @SerializedName("data")
    val data: List<UserListData>? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
)