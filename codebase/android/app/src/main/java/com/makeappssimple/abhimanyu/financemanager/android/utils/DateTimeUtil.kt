package com.makeappssimple.abhimanyu.financemanager.android.utils

import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.dayOfMonth
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.month
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.setDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.setTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.year
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
    timestamp: Long,
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }
    return calendar.formattedDateAndTime()
}

fun getReadableDateAndTimeString(
    timestamp: Long = System.currentTimeMillis(),
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }
    return calendar.formattedReadableDateAndTime()
}

fun getCurrentDayStartingTimestamp(): Long {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.setDate(
        dayOfMonth = calendar.dayOfMonth,
        month = calendar.month,
        year = calendar.year,
    )
    calendar.setTime(
        hour = 0,
        minute = 0,
    )
    return calendar.timeInMillis
}

fun getCurrentMonthStartingTimestamp(): Long {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.setDate(
        dayOfMonth = 1,
        month = calendar.month,
        year = calendar.year,
    )
    calendar.setTime(
        hour = 0,
        minute = 0,
    )
    return calendar.timeInMillis
}

fun getCurrentYearStartingTimestamp(): Long {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.setDate(
        dayOfMonth = 1,
        month = 1,
        year = calendar.year,
    )
    calendar.setTime(
        hour = 0,
        minute = 0,
    )
    return calendar.timeInMillis
}
