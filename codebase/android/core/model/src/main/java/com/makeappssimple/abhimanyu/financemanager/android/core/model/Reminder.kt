package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Daily 09:30 PM
 */
const val DEFAULT_REMINDER_HOUR = 21
const val DEFAULT_REMINDER_MIN = 30

@Serializable
data class Reminder(
    @SerialName(value = "is_enabled")
    val isEnabled: Boolean = false,

    @SerialName(value = "hour")
    val hour: Int = DEFAULT_REMINDER_HOUR,

    @SerialName(value = "min")
    val min: Int = DEFAULT_REMINDER_MIN,
)
