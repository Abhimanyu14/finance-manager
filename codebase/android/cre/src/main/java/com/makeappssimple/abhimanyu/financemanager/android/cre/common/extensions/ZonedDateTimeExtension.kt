package com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions

import java.time.ZonedDateTime

/**
 * [ZonedDateTime] to [Long].
 */
public fun ZonedDateTime.toEpochMilli(): Long {
    return this
        .toInstant()
        .toEpochMilli()
}
