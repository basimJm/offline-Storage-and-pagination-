package com.blackhand.trainingapp.data.service

import retrofit2.http.GET

interface UserService {

    @GET("/user")
    suspend fun getUser()
}