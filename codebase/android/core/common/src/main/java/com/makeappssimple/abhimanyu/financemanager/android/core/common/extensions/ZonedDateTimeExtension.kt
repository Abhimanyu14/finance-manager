package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import java.time.ZonedDateTime

/**
 * [ZonedDateTime] to [Long]
 */
fun ZonedDateTime.toEpochMilli(): Long {
    return this
        .toInstant()
        .toEpochMilli()
}
