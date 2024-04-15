package com.blackhand.trainingapp.data.datasource.remote

import com.blackhand.trainingapp.domain.model.remote.UserRemoteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/user")
    suspend fun getUser(@Query("page") page :Int?):Response<UserRemoteModel?>
}