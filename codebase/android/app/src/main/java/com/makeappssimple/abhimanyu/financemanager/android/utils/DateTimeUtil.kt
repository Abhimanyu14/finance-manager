package com.makeappssimple.abhimanyu.financemanager.android.utils

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

fun getDateString(
    timestamp: Long,
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }

    return DateFormat.format("dd MMM, yyyy", calendar).toString()
}

fun getDateAndTimeString(
    timestamp: Long = System.currentTimeMillis(),
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }

    return "${DateFormat.format("dd MMM, yyyy", calendar)}" +
            " at " +
            "${DateFormat.format("hh:mm a", calendar)}"
}
