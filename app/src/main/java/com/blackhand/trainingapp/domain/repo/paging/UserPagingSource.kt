package com.blackhand.trainingapp.domain.repo.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blackhand.trainingapp.data.service.UserService
import com.blackhand.trainingapp.domain.model.UserListData
import java.io.IOException
import javax.inject.Inject

class UserPagingSource @Inject constructor(private val userService: UserService) :
    PagingSource<Int, UserListData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListData> {
        val position = params.key ?: 1
        return try {
            val response = userService.getUser(position)

            LoadResult.Page(
                data = response.body()?.data ?: emptyList(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.body()?.totalPages) null else position + 1
            )
        } catch (e: IOException) {
            Log.d("LOADINGERRORS", "Network error: ${e.message}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.d("LOADINGERRORS", "Error loading data: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
