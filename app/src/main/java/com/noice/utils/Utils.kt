package com.noice.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getCurrentTimeStamp() : String {
        val formatter = SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ROOT)
        val date = Calendar.getInstance().time
        return formatter.format(date)
    }

    fun getParsedDate(timeStamp: String?) : String {
        if(timeStamp != null) {
            var formatter = SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ROOT)
            val date = formatter.parse(timeStamp)
            if (date != null) {
                formatter = SimpleDateFormat("dd MMM", Locale.ROOT)
                return formatter.format(date)
            }
        }

        return ""
    }
}