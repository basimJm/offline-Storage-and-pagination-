package com.blackhand.trainingapp.domain.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.blackhand.trainingapp.data.datasource.remote.UserService
import com.blackhand.trainingapp.data.db.MainDataBase
import com.blackhand.trainingapp.domain.repo.paging.UserRemoteMediator
import javax.inject.Inject

@ExperimentalPagingApi
class UserRepository @Inject constructor(
    private val userService: UserService,
    private val mainDataBase: MainDataBase
) {

    val getUsers = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = UserRemoteMediator(userService, mainDataBase),
        pagingSourceFactory = { mainDataBase.userDao().getUser() }
    ).liveData
}