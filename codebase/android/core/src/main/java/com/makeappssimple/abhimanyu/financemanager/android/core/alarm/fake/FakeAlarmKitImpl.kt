package com.makeappssimple.abhimanyu.financemanager.android.core.alarm.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKit

public class FakeAlarmKitImpl : AlarmKit {
    public override suspend fun cancelReminderAlarm(): Boolean {
        return true
    }

    public override suspend fun scheduleReminderAlarm(): Boolean {
        return true
    }
}
