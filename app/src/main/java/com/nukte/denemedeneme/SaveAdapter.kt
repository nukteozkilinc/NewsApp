package com.nukte.denemedeneme

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nukte.denemedeneme.databinding.SaveRecyclerLayoutBinding
import com.nukte.denemedeneme.model.News
import java.time.format.DateTimeFormatter

class SaveAdapter() : androidx.recyclerview.widget.ListAdapter<News, SaveAdapter.SaveViewHolder>(NewsComparator) {
    var onItemClicked: ((news: News) -> Unit)? = null
    var onUnsaveButtonClicked: ((news: News) -> Unit)? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val dateTimeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    class SaveViewHolder (val binding : SaveRecyclerLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindSaveItems(news: News){
                Glide.with(itemView).load(news.media).into(binding.saveImageView)
                binding.saveDescriptionText.text = news.excerpt
                binding.saveTitleText.text = news.title
                binding.savePublishedAt.text = news.published_date
                binding.saveButton.setFavorite(news.isSaved,false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val binding = SaveRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return SaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        holder.bindSaveItems(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(getItem(position))

        }

        holder.binding.saveButton.setOnFavoriteAnimationEndListener{_,favorite ->
            when(favorite){
                false -> onUnsaveButtonClicked?.invoke(getItem(position))
            }
        }


    }
}
