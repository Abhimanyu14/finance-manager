package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedReadableDateAndTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
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

fun getStartOfDayTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    return LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .toLocalDate()
        .atStartOfDay()
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}

fun getEndOfDayTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    return LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .toLocalDate()
        .atTime(23, 59, 59)
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}

fun getStartOfMonthTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    return LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .withDayOfMonth(1)
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}

fun getEndOfMonthTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    val localDateTime: LocalDateTime = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    val yearMonth: LocalDate = YearMonth.from(localDateTime).atEndOfMonth()
    return yearMonth
        .atTime(23, 59, 59)
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}

fun getStartOfYearTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    return LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .withMonth(1)
        .withDayOfMonth(1)
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}

fun getEndOfYearTimestamp(
    timestamp: Long = System.currentTimeMillis(),
): Long {
    return LocalDateTime
        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        .withMonth(12)
        .withDayOfMonth(31)
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
}
