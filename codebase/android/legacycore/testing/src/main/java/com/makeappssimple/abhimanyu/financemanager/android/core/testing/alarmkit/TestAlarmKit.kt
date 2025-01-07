package com.makeappssimple.abhimanyu.financemanager.android.core.testing.alarmkit

import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit

public class TestAlarmKit : AlarmKit {
    override suspend fun cancelReminderAlarm(): Boolean {
        return true
    }

    override suspend fun setReminderAlarm(): Boolean {
        return true
    }
}
