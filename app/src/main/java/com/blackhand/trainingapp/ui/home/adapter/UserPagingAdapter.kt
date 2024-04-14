package com.blackhand.trainingapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blackhand.trainingapp.databinding.UserItemListBinding
import com.blackhand.trainingapp.domain.model.UserListData

class UserPagingAdapter() :
    PagingDataAdapter<UserListData, UserPagingAdapter.UserViewHolder>(UserComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position).let { userListData -> holder.bind(userListData) }
    }

    inner class UserViewHolder(private val binding: UserItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserListData?) {
            binding.data = data
        }
    }

    object UserComparator : DiffUtil.ItemCallback<UserListData>() {
        override fun areItemsTheSame(oldItem: UserListData, newItem: UserListData): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserListData, newItem: UserListData): Boolean {
            return oldItem == newItem
        }
    }
}