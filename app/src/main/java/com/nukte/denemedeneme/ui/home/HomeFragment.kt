package com.nukte.denemedeneme.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.engine.Resource
import com.nukte.denemedeneme.*
import com.nukte.denemedeneme.base.BaseFragment
import com.nukte.denemedeneme.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val newsAdapter by lazy { ItemListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHomeRecyclerView()
        initFloatActionButtonActions()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener{
            newsAdapter.refresh() //paging var diye fetchnews ile istek atmana gerek yok
        }
    }

    private fun observeViewModel() = with(homeViewModel) {

        lifecycleScope.launchWhenCreated {
            newsFlow.collectLatest{
                binding.progressBar.isVisible = false
                binding.swipeRefresh.isRefreshing = false
                newsAdapter.submitData(it)
            }
        }
    }


    private fun initFloatActionButtonActions() = with(binding.fabMenuActions){
        setOnClickListener{
            if(binding.fabMenuActions.tag == "UNCLICK"){
                binding.fabMenuActions.tag = "CLICK"
                binding.fabMenuSearch.visibility = View.VISIBLE

            }else{
                binding.fabMenuActions.tag = "UNCLICK"
                binding.fabMenuSearch.visibility = View.GONE

            }
        }
        binding.fabMenuSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToSearchFragment()
            findNavController().navigate(action)
        }
    }

    private fun initHomeRecyclerView() {
        with(newsAdapter) {
            binding.recyclerView.adapter = this

            onItemClicked = {
                val action = HomeFragmentDirections.actionNavigationHomeToDetailScreen(it)
                findNavController().navigate(action)
            }

            onSaveButtonClicked = {
                homeViewModel.saveNews(it)
            }

            onUnsaveButtonClicked = {
                homeViewModel.deleteNews(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        newsAdapter.refresh()
    }
}