package com.makeappssimple.abhimanyu.financemanager.android.cre.common.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

public interface DateTimeUtil : DateUtil, TimeUtil {
    public fun getCurrentLocalDateTime(): LocalDateTime

    public fun getCurrentInstant(): Instant

    public fun getCurrentTimeMillis(): Long

    public fun getSystemDefaultZoneId(): ZoneId

    /**
     * Sample format - 30 Mar, 2023.
     */
    public fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - Monday.
     */
    public fun getFormattedDayOfWeek(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar.
     */
    public fun getFormattedDay(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - March, 2023.
     */
    public fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023.
     */
    public fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 2023-Mar-30, 08-24 AM.
     */
    public fun getFormattedDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM.
     */
    public fun getReadableDateAndTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): String

    public fun getTimestamp(
        date: LocalDate = getLocalDate(),
        time: LocalTime = getLocalTime(),
    ): Long

    public fun getStartOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfDayTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getStartOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfMonthTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getStartOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long

    public fun getEndOfYearTimestamp(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): Long
}
