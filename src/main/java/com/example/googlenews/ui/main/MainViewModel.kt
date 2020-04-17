package com.example.googlenews.ui.main


import androidx.lifecycle.MutableLiveData
import com.example.googlenews.base.BaseViewModel
import com.example.googlenews.data.local.dao.GoogleNewsDao
import com.example.googlenews.model.GoogleNewsResponse
import com.example.googlenews.repository.GoogleNewsRepository
import com.example.googlenews.util.UseCaseResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private var googleNewsRepository: GoogleNewsRepository,
    private var newsDao: GoogleNewsDao
) : BaseViewModel() {

    var googleNewsList = MutableLiveData<GoogleNewsResponse>()
    var newsList = MutableLiveData<GoogleNewsResponse>()
    val showError = MutableLiveData<String>()

    fun fetchGoogleNews(news: String) {
        launch {
            val googleNewsResult = withContext(IO) {
                googleNewsRepository.getGoogleNews(news)
            }

            when (googleNewsResult) {
                is UseCaseResult.Success -> googleNewsList.value =
                    googleNewsResult.data

                is UseCaseResult.Error -> showError.value =
                    googleNewsResult.exception.localizedMessage
            }
        }
    }

    fun saveDataToDb(googleNewsResponse: GoogleNewsResponse) {
        launch {
            withContext(IO) {
                newsDao.insertAll(googleNewsResponse)
            }
        }
    }

    fun update(googleNewsResponse: GoogleNewsResponse) {
        launch {
            withContext(IO) {
                newsDao.update(googleNewsResponse)
            }
        }
    }

    fun fetchDataFromDataBase() {
        launch {
            withContext(IO) {
                newsList.postValue(newsDao.getAllNews())
            }
        }
    }

    fun getShared(key: String): Boolean? {
        return googleNewsRepository.getShared(key)
    }


    override fun onCleared() {
        super.onCleared()
    }
}