package com.blackhand.trainingapp.data.di

import com.blackhand.trainingapp.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder().connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://pagination-api-evg2.onrender.com")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideDataSource(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}