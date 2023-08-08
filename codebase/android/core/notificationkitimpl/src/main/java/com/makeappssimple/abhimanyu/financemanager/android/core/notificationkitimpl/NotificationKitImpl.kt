package com.makeappssimple.abhimanyu.financemanager.android.core.notificationkitimpl

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.makeappssimple.abhimanyu.financemanager.android.core.appkit.AppKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit.NotificationConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit.NotificationKit

class NotificationKitImpl(
    private val appKit: AppKit,
    private val context: Context,
    private val myLogger: MyLogger,
) : NotificationKit {
    override fun scheduleNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        val intent = appKit.getMainActivityIntent().apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        myLogger.logError(
            message = "Sending Notification : ${System.currentTimeMillis()}",
        )
        val notification =
            NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.notification)
                .setColor(Color.parseColor("#DAF5D7"))
                .setColorized(true)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        notificationManager.notify(1, notification)
        myLogger.logError(
            message = "Notification : ${System.currentTimeMillis()}",
        )
    }
}
