package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Daily 09:30 PM.
 */
public object ReminderConstant {
    public const val DEFAULT_REMINDER_HOUR: Int = 21
    public const val DEFAULT_REMINDER_MIN: Int = 30
}

@Serializable
public data class Reminder(
    @SerialName(value = "is_enabled")
    val isEnabled: Boolean = false,

    @SerialName(value = "hour")
    val hour: Int = ReminderConstant.DEFAULT_REMINDER_HOUR,

    @SerialName(value = "min")
    val min: Int = ReminderConstant.DEFAULT_REMINDER_MIN,
)
