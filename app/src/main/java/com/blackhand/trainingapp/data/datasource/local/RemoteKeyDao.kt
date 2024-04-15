package com.blackhand.trainingapp.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blackhand.trainingapp.domain.model.local.UserRemoteKey

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM UserRemoteKey WHERE id =:id")
    suspend fun getRemoteKeys(id: String): UserRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<UserRemoteKey>)

    @Query(" DELETE FROM userremotekey")
    suspend fun deleteAllRemoteKeys()
}