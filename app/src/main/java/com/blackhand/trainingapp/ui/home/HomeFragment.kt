package com.blackhand.trainingapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.blackhand.trainingapp.R
import com.blackhand.trainingapp.databinding.FragmentHomeBinding
import com.blackhand.trainingapp.ui.home.adapter.LoaderAdapter
import com.blackhand.trainingapp.ui.home.adapter.UserPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: UserPagingAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initAdapter() {
        adapter = UserPagingAdapter()
        binding.rvUsers.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter { adapter.retry() },
            footer = LoaderAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { state ->
            binding.btnRetry.isVisible =
                state.source.refresh is LoadState.Error || state.source.append is LoadState.Error
            binding.pbLoader.isVisible = state.source.refresh is LoadState.Loading
            binding.rvUsers.isVisible = state.source.refresh is LoadState.NotLoading
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            homeViewModel.usersList.observe(viewLifecycleOwner) { data ->

                adapter.submitData(lifecycle, data)
            }
        }
    }


}