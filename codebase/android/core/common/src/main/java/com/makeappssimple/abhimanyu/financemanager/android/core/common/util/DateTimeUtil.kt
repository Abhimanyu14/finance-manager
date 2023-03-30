package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId

fun getCurrentLocalDate(): LocalDate {
    return LocalDate.now()
}

fun getCurrentLocalTime(): LocalTime {
    return LocalTime.now()
}

fun getCurrentLocalDateTime(): LocalDateTime {
    return LocalDateTime.now()
}

fun getCurrentInstant(): Instant {
    return Instant.now()
}

fun getCurrentTimeMillis(): Long {
    return getCurrentInstant().toEpochMilli()
}

fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}

fun getFormattedDate(
    timestamp: Long,
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedDate(
            zoneId = zoneId,
        )
}

fun getFormattedDateAndTime(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedDateAndTime(
            zoneId = zoneId,
        )
}

fun getReadableDateAndTime(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedReadableDateAndTime(
            zoneId = zoneId,
        )
}

fun getLocalDate(
    timestamp: Long,
    zoneId: ZoneId = getSystemDefaultZoneId(),
): LocalDate {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
}

fun getLocalTime(
    timestamp: Long,
    zoneId: ZoneId = getSystemDefaultZoneId(),
): LocalTime {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalTime()
}

fun getLocalDateTime(
    timestamp: Long,
    zoneId: ZoneId = getSystemDefaultZoneId(),
): LocalDateTime {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDateTime()
}

fun getStartOfDayTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
        .atStartOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}

fun getEndOfDayTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
        .atEndOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}

fun getStartOfMonthTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
        .withDayOfMonth(1)
        .atStartOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}

fun getEndOfMonthTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    val localDateTime: LocalDate = Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
    val localDate: LocalDate = YearMonth
        .from(localDateTime)
        .atEndOfMonth()
    return localDate
        .atEndOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}

fun getStartOfYearTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
        .withMonth(1)
        .withDayOfMonth(1)
        .atStartOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}

fun getEndOfYearTimestamp(
    timestamp: Long = getCurrentTimeMillis(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    val localDateTime: LocalDate = Instant
        .ofEpochMilli(timestamp)
        .toZonedDateTime(
            zoneId = zoneId,
        )
        .toLocalDate()
        .withMonth(12)
    val localDate: LocalDate = YearMonth
        .from(localDateTime)
        .atEndOfMonth()
    return localDate
        .atEndOfDay()
        .toEpochMilli(
            zoneId = zoneId,
        )
}
