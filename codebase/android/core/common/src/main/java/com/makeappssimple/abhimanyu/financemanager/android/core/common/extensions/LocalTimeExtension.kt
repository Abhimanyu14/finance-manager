package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Sample format - 08:24 AM
 */
fun LocalTime.formattedTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}

private fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}
