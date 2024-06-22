package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit

public interface AlarmKit {
    public suspend fun cancelReminderAlarm(): Boolean

    public suspend fun setReminderAlarm(): Boolean
}
