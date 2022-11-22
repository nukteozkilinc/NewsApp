package com.nukte.denemedeneme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nukte.denemedeneme.databinding.SearchRecyclerLayoutBinding
import com.nukte.denemedeneme.model.News

class SearchAdapter() : ListAdapter<News,SearchAdapter.SearchAdapterViewHolder> (NewsComparator){
    var onItemClicked: ((news: News) -> Unit)? = null
    var onSaveButtonClicked: ((news: News) -> Unit)? = null
    var onUnsaveButtonClicked: ((news: News) -> Unit)? = null

    class SearchAdapterViewHolder(val binding: SearchRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun searchBindItems(news: News) {
            binding.searchSaveButton.setFavorite(news.isSaved, false)
            Glide.with(itemView).load(news.media).into(binding.searchImageView)
            binding.searchDescriptionText.text = news.excerpt
            binding.searchTitleText.text = news.title
            binding.searchPublishedAt.text = news.published_date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val binding =
            SearchRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        holder.searchBindItems(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(getItem(position))
        }

        holder.binding.searchSaveButton.setOnFavoriteChangeListener { _, favorite ->
            when (favorite) {
                true -> onSaveButtonClicked?.invoke(getItem(position))
                false -> onUnsaveButtonClicked?.invoke(getItem(position))
            }
        }
    }
}