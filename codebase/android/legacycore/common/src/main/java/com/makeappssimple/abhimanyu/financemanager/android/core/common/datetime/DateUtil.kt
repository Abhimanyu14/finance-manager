package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

public interface DateUtil {
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

    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
