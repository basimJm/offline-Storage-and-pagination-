package com.blackhand.trainingapp.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blackhand.trainingapp.domain.model.remote.UserListData

@Dao
interface UserDao {

    @Query("SELECT * FROM users ")
    fun getUser(): PagingSource<Int, UserListData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUsers(users: List<UserListData>)

    @Query(" DELETE FROM users")
    suspend fun deleteAllUsers()
}