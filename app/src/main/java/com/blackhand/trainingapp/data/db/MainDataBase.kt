package com.blackhand.trainingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blackhand.trainingapp.data.datasource.local.RemoteKeyDao
import com.blackhand.trainingapp.data.datasource.local.UserDao
import com.blackhand.trainingapp.domain.model.local.UserRemoteKey
import com.blackhand.trainingapp.domain.model.remote.UserListData

@Database(entities = [UserListData::class, UserRemoteKey::class], version = 1)
abstract class MainDataBase :RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}