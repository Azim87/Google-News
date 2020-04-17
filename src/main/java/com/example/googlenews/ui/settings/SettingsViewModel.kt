package com.example.googlenews.ui.settings

import com.example.googlenews.base.BaseViewModel
import com.example.googlenews.repository.GoogleNewsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val repositoryImpl: GoogleNewsRepository
) : BaseViewModel() {

    fun saveToShared(key: String, value: Boolean) {
        launch {
            withContext(IO) {
                repositoryImpl.putShared(key, value)
            }
        }
    }

    fun getShared(key: String): Boolean {
        return repositoryImpl.getShared(key)
    }
}