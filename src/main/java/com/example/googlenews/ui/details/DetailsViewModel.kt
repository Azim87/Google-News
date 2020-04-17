package com.example.googlenews.ui.details

import com.example.googlenews.base.BaseViewModel
import com.example.googlenews.repository.GoogleNewsRepository

class DetailsViewModel(
    private val repository: GoogleNewsRepository
) : BaseViewModel() {

    fun getShared(key: String): Boolean {
        return repository.getShared(key)
    }
}