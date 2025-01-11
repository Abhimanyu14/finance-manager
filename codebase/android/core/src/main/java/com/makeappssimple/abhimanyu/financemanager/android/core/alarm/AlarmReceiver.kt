package com.makeappssimple.abhimanyu.financemanager.android.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class AlarmReceiver : BroadcastReceiver() {
    @Inject
    public lateinit var logKit: LogKit

//    @Inject
//    public lateinit var notificationKit: NotificationKit

    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        logKit.logInfo(
            message = "Alarm received : ${System.currentTimeMillis()}",
        )
//        notificationKit.scheduleNotification()
    }
}
