package com.blackhand.trainingapp.domain.repo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blackhand.trainingapp.data.datasource.remote.UserService
import com.blackhand.trainingapp.data.db.MainDataBase
import com.blackhand.trainingapp.domain.model.local.UserRemoteKey
import com.blackhand.trainingapp.domain.model.remote.UserListData

@ExperimentalPagingApi
class UserRemoteMediator(
    private val userService: UserService,
    private val quoteDatabase: MainDataBase
) : RemoteMediator<Int, UserListData>() {

    private val userDao = quoteDatabase.userDao()
    private val userRemoteKeyDao = quoteDatabase.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserListData>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            // 1- get data from api
            val response = userService.getUser(currentPage)
            // 2- determine when result of mediator is success
            val endOfPaginationReached = response.body()?.totalPages == currentPage

            // Handle prev and next key
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // 3- Handle all DB Operations in withTransaction to ensure all operations success together
            quoteDatabase.withTransaction {

                //Every time load type is refresh dump db
                if (loadType == LoadType.REFRESH) {
                    userDao.deleteAllUsers()
                    userRemoteKeyDao.deleteAllRemoteKeys()
                }
                // then dill db with latest data from api
                response.body()?.data?.let { userDao.insertListOfUsers(it) }
                val keys = response.body()?.data?.map { user ->
                    UserRemoteKey(
                        id = user._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                // and insert remote keys for offline pagination
                keys?.let { userRemoteKeyDao.insertAllRemoteKeys(it) }
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserListData>
    ): UserRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                userRemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UserListData>
    ): UserRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                userRemoteKeyDao.getRemoteKeys(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UserListData>
    ): UserRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                userRemoteKeyDao.getRemoteKeys(id = user._id)
            }
    }
}