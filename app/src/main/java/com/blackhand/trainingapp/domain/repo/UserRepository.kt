package com.blackhand.trainingapp.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.blackhand.trainingapp.data.service.UserService
import com.blackhand.trainingapp.domain.repo.paging.UserPagingSource
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {

    val getUsers = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { UserPagingSource(userService) }
    ).liveData
}