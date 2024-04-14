package com.blackhand.trainingapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blackhand.trainingapp.databinding.LoaderItemBinding

class LoaderAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {


    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }

    inner class LoaderViewHolder(private val binding: LoaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loader: LoadState) {
            binding.pbLoader.isVisible = loader is LoadState.Loading
            binding.tvError.isVisible = loader !is LoadState.Loading
            binding.btnRetry.isVisible = loader !is LoadState.Loading

            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}