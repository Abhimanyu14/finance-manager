package com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.fake

import com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.AlarmKit

public class FakeAlarmKitImpl : AlarmKit {
    public override suspend fun cancelReminderAlarm(): Boolean {
        return true
    }

    public override suspend fun setReminderAlarm(): Boolean {
        return true
    }
}
