package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

public interface TimeKit {
    public fun getCurrentLocalTime(): LocalTime

    public fun getLocalTime(
        timestamp: Long = getCurrentTimeMillis(),
        zoneId: ZoneId = getSystemDefaultZoneId(),
    ): LocalTime

    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
