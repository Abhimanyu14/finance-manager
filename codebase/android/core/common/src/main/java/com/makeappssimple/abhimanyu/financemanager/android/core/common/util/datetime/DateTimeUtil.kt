package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

interface DateTimeUtil {
    fun getCurrentLocalDate(): LocalDate

    fun getCurrentLocalTime(): LocalTime

    fun getCurrentLocalDateTime(): LocalDateTime

    fun getCurrentInstant(): Instant

    fun getCurrentTimeMillis(): Long

    fun getSystemDefaultZoneId(): ZoneId

    /**
     * Sample format - 30 Mar, 2023
     */
    fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar
     */
    fun getFormattedDay(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - March, 2023
     */
    fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023
     */
    fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023-Mar-30, 08-24 AM
     */
    fun getFormattedDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM
     */
    fun getReadableDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    fun getLocalDate(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    fun getLocalTime(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalTime

    fun getLocalDateTime(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDateTime

    fun getStartOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getStartOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getStartOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    fun getEndOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long
}
