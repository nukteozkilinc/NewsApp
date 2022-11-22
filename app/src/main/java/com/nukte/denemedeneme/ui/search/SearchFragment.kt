package com.nukte.denemedeneme.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nukte.denemedeneme.R
import com.nukte.denemedeneme.SearchAdapter
import com.nukte.denemedeneme.base.BaseFragment
import com.nukte.denemedeneme.databinding.FragmentSearchBinding
import com.nukte.denemedeneme.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter() }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initSearchView()
        initAdapterClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() = with(searchViewModel) {
        news.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }
    }

    private fun initAdapterClickListeners() = with(searchAdapter) {
        onItemClicked = {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailScreen(it)
            findNavController().navigate(action)
        }
        onSaveButtonClicked = { searchViewModel.saveNews(it) }
        onUnsaveButtonClicked = { searchViewModel.deleteNews(it) }
    }

    private fun initSearchView() = with(binding.searchView) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchViewModel.getSearchNews(p0.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun initRecycler() = with(binding.searchRecyclerView) {
        adapter = searchAdapter
    }

    override fun onResume() {
        super.onResume()
        searchViewModel.refreshSearchNews()
    }
}