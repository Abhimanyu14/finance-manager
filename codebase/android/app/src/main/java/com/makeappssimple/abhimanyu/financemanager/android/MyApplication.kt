package com.makeappssimple.abhimanyu.financemanager.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit.NotificationConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        val channel = NotificationChannel(
            NotificationConstants.CHANNEL_ID_REMINDER,
            NotificationConstants.CHANNEL_NAME_REMINDER,
            NotificationManager.IMPORTANCE_HIGH,
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
