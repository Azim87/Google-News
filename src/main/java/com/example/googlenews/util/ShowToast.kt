package com.example.googlenews.util

import android.widget.Toast
import com.example.googlenews.App

class ShowToast {
    companion object {
        fun message(message: String) {
            Toast.makeText(App().getInstance(), message, Toast.LENGTH_SHORT).show()
        }
    }
}