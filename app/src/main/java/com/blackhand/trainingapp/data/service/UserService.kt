package com.blackhand.trainingapp.data.service

import com.blackhand.trainingapp.domain.model.UserListData
import com.blackhand.trainingapp.domain.model.UserRemoteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/user")
    suspend fun getUser(@Query("page") page :Int?):Response<UserRemoteModel?>
}