package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit

public class FakeAlarmKitImpl : AlarmKit {
    public override suspend fun cancelReminderAlarm(): Boolean {
        return true
    }

    public override suspend fun setReminderAlarm(): Boolean {
        return true
    }
}
