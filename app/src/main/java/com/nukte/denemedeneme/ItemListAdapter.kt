package com.nukte.denemedeneme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nukte.denemedeneme.databinding.RecyclerLayoutBinding
import com.nukte.denemedeneme.model.News

class ItemListAdapter() : PagingDataAdapter<News, ItemListAdapter.ItemListViewHolder>(NewsComparator) {

    var onItemClicked: ((news: News) -> Unit)? = null
    var onSaveButtonClicked: ((news: News) -> Unit)? = null
    var onUnsaveButtonClicked: ((news: News) -> Unit)? = null

    class ItemListViewHolder(val binding: RecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(news: News) {
            binding.saveButton.setFavorite(news.isSaved, false)
            Glide.with(itemView).load(news.media).into(binding.imageView)
            binding.descriptionText.text = news.excerpt
            binding.titleText.text = news.title
            binding.publishedAt.text = news.published_date
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding =
            RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        getItem(position)?.let { news ->
            holder.bindItems(news)
            holder.itemView.setOnClickListener {
                onItemClicked?.invoke(news)
            }
            holder.binding.saveButton.setOnFavoriteChangeListener { _, favorite ->
                when (favorite) {
                    true -> onSaveButtonClicked?.invoke(news)
                }
            }
            holder.binding.saveButton.setOnFavoriteAnimationEndListener{_,favorite ->
                when(favorite){
                    false -> onUnsaveButtonClicked?.invoke(news)
                }
            }
        }
    }
}

object NewsComparator : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
        oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
        oldItem == newItem
}

