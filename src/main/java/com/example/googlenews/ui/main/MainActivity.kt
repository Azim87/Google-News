package com.example.googlenews.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlenews.R
import com.example.googlenews.model.Articles
import com.example.googlenews.model.GoogleNewsResponse
import com.example.googlenews.ui.details.DetailsActivity
import com.example.googlenews.ui.main.adapter.GoogleNewsAdapter
import com.example.googlenews.ui.settings.SettingsActivity
import com.example.googlenews.util.ConnectionHelper
import com.example.googlenews.util.PreferenceHelper
import com.example.googlenews.util.SHARED_KEY
import com.example.googlenews.util.ShowToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), GoogleNewsAdapter.OnItemPositionClickListener {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var rootLayout: CoordinatorLayout
    private lateinit var newsAdapter: GoogleNewsAdapter
    private lateinit var mProgress: ProgressBar
    private lateinit var connectivityReceiver: BroadcastReceiver
    private lateinit var mToolbar: Toolbar

    private var newsData: ArrayList<Articles> = ArrayList()
    private var newsLocalData: ArrayList<Articles> = ArrayList()
    private val sharedPref = PreferenceHelper()
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSavedState()

        setContentView(R.layout.activity_main)

        connectivityReceiver = ConnectivityReceiver()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, intentFilter)
        initViews()
        initRecyclerView()
        initToolbar()
    }

    private fun getSavedState() {
        if (mainViewModel.getShared(SHARED_KEY)!!) {
            setTheme(R.style.DarkTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        } else {
            setTheme(R.style.AppTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }

    private fun initViews() {
        mRecyclerView = findViewById(R.id.main_recycler)
        rootLayout = findViewById(R.id.root_layout)
        mProgress = findViewById(R.id.progress_bar)
        mToolbar = findViewById(R.id.toolbar)
    }

    private fun initRecyclerView() {
        mRecyclerView.apply {
            newsAdapter = GoogleNewsAdapter(this@MainActivity)
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initToolbar() {
        mToolbar.inflateMenu(R.menu.toolbar_menu)
        mToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun fetchDataFromServer() {
        mainViewModel.googleNewsList.observe(this, Observer { googleNews ->
            newsData = googleNews.articles as ArrayList<Articles>
            insertToDataBase(googleNews)
            when {
                googleNews != null -> {
                    mainViewModel.update(googleNews)
                    updateAdapter(googleNews.articles)
                }
            }
        })
        mainViewModel.fetchGoogleNews(getString(R.string.google_news))
    }

    private fun getNewsDataFromDb() {
        mainViewModel.newsList.observe(this@MainActivity, Observer { newsDataFromDB ->
            if (newsDataFromDB != null && !newsDataFromDB.articles.isNullOrEmpty()) {
                newsLocalData = newsDataFromDB.articles as ArrayList<Articles>
                updateAdapter(newsDataFromDB.articles)
            } else {
                fetchDataFromServer()
            }
        })
        mainViewModel.fetchDataFromDataBase()
    }

    private fun updateAdapter(newsData: ArrayList<Articles> = ArrayList()) {
        newsAdapter.setNewsList(newsData)
    }

    private fun insertToDataBase(googleNews: GoogleNewsResponse) {
        mainViewModel.saveDataToDb(googleNews)
    }

    inner class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ConnectionHelper(context!!).isOnline()) {
                fetchDataFromServer()
            } else {
                ShowToast.message("Internet connection currently not available")
                getNewsDataFromDb()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val title = newsLocalData[position].title
        val publised = newsLocalData[position].publishedAt
        val content = newsLocalData[position].content
        val imgUrl = newsLocalData[position].urlToImage

        if (newsLocalData != null) {
            DetailsActivity.start(this, title, publised, content, imgUrl)
        }
    }

    override fun onItemLongClick(position: Int) {
    }
}
