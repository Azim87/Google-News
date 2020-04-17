package com.example.googlenews.util

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun convertToDate(date: String?): String? {
        var newDate = date
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return sdf.format(Date()).also { newDate = it }
    }
}