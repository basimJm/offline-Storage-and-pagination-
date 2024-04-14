package com.blackhand.trainingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blackhand.trainingapp.domain.model.UserListData
import com.blackhand.trainingapp.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _usersList by lazy {
        userRepository.getUsers.cachedIn(viewModelScope)
    }

    val usersList: LiveData<PagingData<UserListData>> get() = _usersList
}
