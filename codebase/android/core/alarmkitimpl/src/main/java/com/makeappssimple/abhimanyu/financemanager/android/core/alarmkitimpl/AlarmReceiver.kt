package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit.NotificationKit
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var myLogger: MyLogger

    @Inject
    lateinit var notificationKit: NotificationKit

    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        myLogger.logError(
            message = "Alarm received : ${System.currentTimeMillis()}",
        )
        notificationKit.scheduleNotification()
    }
}
