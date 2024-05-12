package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

public interface DateUtil {
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }

    public fun getCurrentLocalDate(): LocalDate

    public fun getLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    public fun getStartOfMonthLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate

    public fun getStartOfYearLocalDate(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalDate
}
