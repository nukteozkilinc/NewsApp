package com.nukte.denemedeneme.ui.detail

import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.nukte.denemedeneme.model.News
import com.nukte.denemedeneme.model.NewsResponse
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nukte.denemedeneme.R
import com.nukte.denemedeneme.base.BaseFragment
import com.nukte.denemedeneme.databinding.DetailScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URI

@AndroidEntryPoint
class DetailScreen : BaseFragment<DetailScreenFragmentBinding>(R.layout.detail_screen_fragment) {

    private val args: DetailScreenArgs by navArgs()
    private val detailScreenViewModel: DetailScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareClickListener()
        saveClickListener()


        args.news.let {
            Glide.with(view).load(it.media).into(binding.imageDetail)
            binding.titleDetail.text = it.title
            binding.contentDetail.text = it.summary
            binding.dateDetail.text = it.published_date
            binding.saveButton.isFavorite = it.isSaved
        }
    }

    private fun shareClickListener() = with(binding.shareButton){
        val uri = URI(args.news.link)
        setOnClickListener {
            shareNews(uri)
        }
    }

    private fun saveClickListener() = with(binding.saveButton){
        setOnFavoriteChangeListener { _, favorite ->
            args.news.isSaved = true
            when (favorite) {
                true -> detailScreenViewModel.saveNews(args.news)
                else -> {}
            }

        }
        setOnFavoriteAnimationEndListener { _, favorite ->
            args.news.isSaved = false
            when (favorite) {
                false -> detailScreenViewModel.deleteNews(args.news)
                else -> {}
            }
        }
    }

    private fun shareNews(uri: URI) {
        val intent = Intent()
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Habere Göz At! ${uri.toURL()}")
        intent.type = "text/plugin"
        startActivity(Intent.createChooser(intent, "Share to : "))
    }
}
