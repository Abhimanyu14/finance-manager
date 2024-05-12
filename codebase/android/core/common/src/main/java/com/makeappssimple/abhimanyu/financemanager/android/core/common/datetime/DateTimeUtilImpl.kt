package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedMonth
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedYear
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

private const val lastMonthOfYear = 12

public class DateTimeUtilImpl @Inject constructor() : DateTimeUtil {
    override fun getCurrentLocalDate(): LocalDate {
        return LocalDate.now()
    }

    override fun getCurrentLocalTime(): LocalTime {
        return LocalTime.now()
    }

    override fun getCurrentLocalDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    override fun getCurrentInstant(): Instant {
        return Instant.now()
    }

    override fun getCurrentTimeMillis(): Long {
        return getCurrentInstant().toEpochMilli()
    }

    override fun getSystemDefaultZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }

    /**
     * Sample format - 30 Mar, 2023
     */
    override fun getFormattedDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDate(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - 30 Mar
     */
    override fun getFormattedDay(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDay(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - March, 2023
     */
    override fun getFormattedMonth(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedMonth(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - 2023
     */
    override fun getFormattedYear(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedYear(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - 2023-Mar-30, 08-24 AM
     */
    override fun getFormattedDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedDateAndTime(
                zoneId = zoneId,
            )
    }

    /**
     * Sample format - 30 Mar, 2023 at 08:24 AM
     */
    override fun getReadableDateAndTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): String {
        return Instant
            .ofEpochMilli(timestamp)
            .formattedReadableDateAndTime(
                zoneId = zoneId,
            )
    }

    override fun getTimestamp(
        date: LocalDate,
        time: LocalTime,
        zoneId: ZoneId,
    ): Long {
        return date
            .atTime(time)
            .toEpochMilli()
    }

    override fun getLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): LocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
    }

    override fun getLocalTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): LocalTime {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalTime()
    }

    override fun getLocalDateTime(
        timestamp: Long,
        zoneId: ZoneId,
    ): LocalDateTime {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDateTime()
    }

    override fun getStartOfDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
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

    override fun getEndOfDayTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
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

    override fun getStartOfMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
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

    override fun getStartOfMonthLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): LocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .withDayOfMonth(1)
    }

    override fun getEndOfMonthTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
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

    override fun getStartOfYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
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

    override fun getStartOfYearLocalDate(
        timestamp: Long,
        zoneId: ZoneId,
    ): LocalDate {
        return Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .withMonth(1)
            .withDayOfMonth(1)
    }

    override fun getEndOfYearTimestamp(
        timestamp: Long,
        zoneId: ZoneId,
    ): Long {
        val localDateTime: LocalDate = Instant
            .ofEpochMilli(timestamp)
            .toZonedDateTime(
                zoneId = zoneId,
            )
            .toLocalDate()
            .withMonth(lastMonthOfYear)
        val localDate: LocalDate = YearMonth
            .from(localDateTime)
            .atEndOfMonth()
        return localDate
            .atEndOfDay()
            .toEpochMilli(
                zoneId = zoneId,
            )
    }
}

public fun getLocalDate(
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

public fun getTimestamp(
    localDate: LocalDate,
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return localDate
        .atStartOfDay(zoneId)
        .toEpochMilli()
}

public fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}
