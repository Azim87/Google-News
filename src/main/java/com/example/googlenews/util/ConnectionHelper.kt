package com.example.googlenews.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionHelper(context: Context) {
        private val appContext = context.applicationContext

        fun isOnline(): Boolean {
            val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

}