package com.makeappssimple.abhimanyu.financemanager.android.utils

import android.text.format.DateFormat
import java.util.*

fun getDateString(
    timestamp: Long,
): String {
    val calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = timestamp
    }

    return DateFormat.format("dd MMM yyyy", calendar).toString()
}
