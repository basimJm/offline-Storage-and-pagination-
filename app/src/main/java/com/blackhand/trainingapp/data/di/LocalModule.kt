package com.blackhand.trainingapp.data.di

import android.content.Context
import androidx.room.Room
import com.blackhand.trainingapp.data.datasource.local.RemoteKeyDao
import com.blackhand.trainingapp.data.datasource.local.UserDao
import com.blackhand.trainingapp.data.db.MainDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, MainDataBase::class.java, "usersDB").build()

    @Provides
    @Singleton
    fun provideUserDao(mainDataBase: MainDataBase): UserDao {
        return mainDataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeyDao(mainDataBase: MainDataBase): RemoteKeyDao {
        return mainDataBase.remoteKeyDao()
    }
}