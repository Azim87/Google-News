package com.example.googlenews.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.googlenews.R
import com.example.googlenews.util.DateHelper
import com.example.googlenews.util.SHARED_KEY
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    private val detailsViewModel by viewModel<DetailsViewModel>()

    companion object {
        fun start(
            context: Context,
            title: String,
            publishedAt: String,
            content: String,
            urlToImage: String
        ) {
            val detailIntent = Intent(context, DetailsActivity::class.java)
            detailIntent.putExtra("title", title)
            detailIntent.putExtra("publish", publishedAt)
            detailIntent.putExtra("content", content)
            detailIntent.putExtra("urlToImage", urlToImage)
            detailIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            detailIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(detailIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSavedState()
        setContentView(R.layout.activity_details)
        showDetailNews()
    }

    private fun showDetailNews() {
        news_title.text = intent.getStringExtra("title")
        news_content.text = intent.getStringExtra("content")
        news_category.text =
            """published at: ${DateHelper.convertToDate(intent.getStringExtra("publish"))}"""
        Glide.with(this)
            .load(intent.getStringExtra("urlToImage"))
            .centerCrop()
            .transform(CenterInside(), RoundedCorners(14))
            .into(news_image)
    }

    private fun getSavedState() {
        if (detailsViewModel.getShared(SHARED_KEY)) {
            setTheme(R.style.DarkTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        } else {
            setTheme(R.style.AppTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }
}
