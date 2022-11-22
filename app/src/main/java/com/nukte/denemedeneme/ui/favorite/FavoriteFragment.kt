package com.nukte.denemedeneme.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nukte.denemedeneme.model.News
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.nukte.denemedeneme.R
import com.nukte.denemedeneme.SaveAdapter
import com.nukte.denemedeneme.base.BaseFragment
import com.nukte.denemedeneme.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val saveAdapter by lazy { SaveAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initAdapterClickListener()
        observeViewModel()
    }

    private fun observeViewModel() = with(favoriteViewModel){
         getSavedNews().observe(viewLifecycleOwner) {
            saveAdapter.submitList(it)
        }
    }

    private fun initRecycler() = with(binding.saveRecyclerView) {
        adapter = saveAdapter
    }

    private fun initAdapterClickListener() = with(saveAdapter){
        saveAdapter.onItemClicked = {
            var action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailScreen(it)
            findNavController().navigate(action)
        }
        saveAdapter.onUnsaveButtonClicked = {
            favoriteViewModel.deleteNews(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
        /*private var _binding: FragmentFavoriteBinding? = null
        private val binding get() = _binding!! */
    }
}