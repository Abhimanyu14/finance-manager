package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.datetime.getSystemDefaultZoneId
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Sample format - 08:24 AM.
 */
public fun LocalTime.formattedTime(
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}
