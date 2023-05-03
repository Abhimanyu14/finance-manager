package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * [LocalDate] to [LocalDateTime]
 * Time: 23:59:59
 */
fun LocalDate.atEndOfDay(): LocalDateTime {
    return this.atTime(LocalTime.MAX)
}

/**
 * Sample format - 30 Mar, 2023
 */
fun LocalDate.formattedDate(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

private fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}
