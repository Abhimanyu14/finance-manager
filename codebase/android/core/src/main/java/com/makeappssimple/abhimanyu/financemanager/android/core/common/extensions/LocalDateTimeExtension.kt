package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.getSystemDefaultZoneId
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * [LocalDateTime] to [Long].
 */
public fun LocalDateTime.toEpochMilli(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Long {
    return this
        .toInstant(
            zoneId = zoneId,
        )
        .toEpochMilli()
}

/**
 * [LocalDateTime] to [Instant].
 */
internal fun LocalDateTime.toInstant(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Instant {
    return this
        .atZone(zoneId)
        .toInstant()
}
