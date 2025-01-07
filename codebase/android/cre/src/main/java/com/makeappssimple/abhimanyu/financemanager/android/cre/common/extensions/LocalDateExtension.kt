package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.datetime.getSystemDefaultZoneId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

public fun LocalDate?.orMin(): LocalDate {
    return this ?: LocalDate.MIN
}

/**
 * [LocalDate] to [LocalDateTime].
 * Time: 23:59:59
 */
public fun LocalDate.atEndOfDay(): LocalDateTime {
    return this.atTime(LocalTime.MAX)
}

/**
 * Sample format - 30 Mar, 2023.
 */
public fun LocalDate.formattedDate(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}
