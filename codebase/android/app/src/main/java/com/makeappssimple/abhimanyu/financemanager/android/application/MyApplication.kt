package com.makeappssimple.abhimanyu.financemanager.android.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.makeappssimple.abhimanyu.financemanager.android.core.notification.NotificationConstants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
public class MyApplication : Application(), Configuration.Provider {
    @Inject
    public lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                workerFactory = workerFactory,
            )
            .build()

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
            (getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager) ?: return
        notificationManager.createNotificationChannel(channel)
    }
}
