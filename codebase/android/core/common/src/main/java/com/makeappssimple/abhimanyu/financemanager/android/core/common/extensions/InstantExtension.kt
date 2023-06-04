package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Sample format - 30 Mar, 2023
 */
internal fun Instant.formattedDate(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 30 Mar
 */
internal fun Instant.formattedDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - March, 2023
 */
internal fun Instant.formattedMonth(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("MMMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 2023
 */
internal fun Instant.formattedYear(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("yyyy")
        .withZone(zoneId)
        .format(this)
}

/**
 * Sample format - 2023-Mar-30, 08-24 AM
 */
internal fun Instant.formattedDateAndTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("yyyy-MMM-dd, hh-mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}

/**
 * Sample format - 30 Mar, 2023 at 08:24 AM
 */
internal fun Instant.formattedReadableDateAndTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return "${formattedDate(zoneId)} at ${formattedTime(zoneId)}"
}

/**
 * Sample format - 08:24 AM
 */
internal fun Instant.formattedTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}

/**
 * [Instant] to [ZonedDateTime]
 */
fun Instant.toZonedDateTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): ZonedDateTime {
    return this.atZone(zoneId)
}

/**
 * [Instant] to [Instant]
 */
internal fun Instant.atStartOfDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Instant {
    return this
        .atZone(zoneId)
        .toLocalDate()
        .atStartOfDay()
        .toInstant(
            zoneId = zoneId,
        )
}

/**
 * [Instant] to [Instant]
 */
fun Instant.atEndOfDay(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): Instant {
    return this
        .atZone(zoneId)
        .toLocalDate()
        .atEndOfDay()
        .toInstant(
            zoneId = zoneId,
        )
}

private fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}
