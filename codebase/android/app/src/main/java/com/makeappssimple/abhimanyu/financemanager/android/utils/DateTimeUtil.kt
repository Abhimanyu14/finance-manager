package com.makeappssimple.abhimanyu.financemanager.android.utils

import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedTime
import java.util.Calendar
import java.util.Locale

fun getDateString(
    timestamp: Long,
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }
    return calendar.formattedDate()
}

fun getDateAndTimeString(
    timestamp: Long = System.currentTimeMillis(),
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }
    return "${calendar.formattedDate()} at ${calendar.formattedTime()}"
}
