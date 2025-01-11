package com.makeappssimple.abhimanyu.financemanager.android.core.alarm

public interface AlarmKit {
    public suspend fun cancelReminderAlarm(): Boolean

    public suspend fun setReminderAlarm(): Boolean
}
