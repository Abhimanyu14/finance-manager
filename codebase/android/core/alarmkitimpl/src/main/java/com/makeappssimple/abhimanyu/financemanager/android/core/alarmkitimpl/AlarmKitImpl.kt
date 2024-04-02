package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.boot.BootCompleteReceiver
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.time.TimeChangedReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalTime

class AlarmKitImpl(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeUtil: DateTimeUtil,
    private val myLogger: MyLogger,
    private val myPreferencesRepository: MyPreferencesRepository,
) : AlarmKit {
    // TODO(Abhi): Return status of alarm
    override fun enableReminder() {
        CoroutineScope(
            context = dispatcherProvider.io,
        ).launch {
            val reminder = myPreferencesRepository.getReminder().first() ?: Reminder()
            val initialAlarmTimestamp = dateTimeUtil.getTimestamp(
                time = LocalTime.of(reminder.hour, reminder.min)
            )
            myLogger.logError(
                message = "Alarm set for : ${reminder.hour}:${reminder.min}",
            )

            val alarmManager = getAlarmManager() ?: return@launch
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                getAlarmReceiverIntent(),
                PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setRepeating(
                AlarmManager.RTC,
                initialAlarmTimestamp,
                AlarmManager.INTERVAL_DAY,
                pendingIntent,
            )

            enableBroadcastReceivers()
            myPreferencesRepository.setIsReminderEnabled(
                isReminderEnabled = true,
            )
        }
    }

    // TODO(Abhi): Return status of alarm
    override fun disableReminder() {
        myLogger.logError(
            message = "Alarm cleared",
        )
        val alarmManager = getAlarmManager() ?: return
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            getAlarmReceiverIntent(),
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)

        disableBroadcastReceivers()

        CoroutineScope(
            context = dispatcherProvider.io,
        ).launch {
            myPreferencesRepository.setIsReminderEnabled(
                isReminderEnabled = false,
            )
        }
    }

    private fun getAlarmManager(): AlarmManager? {
        return context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    private fun getAlarmReceiverIntent(): Intent {
        return Intent(context, AlarmReceiver::class.java)
    }

    private fun enableBroadcastReceivers() {
        val bootComplete = ComponentName(context, BootCompleteReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            bootComplete,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        val timeChanged = ComponentName(context, TimeChangedReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            timeChanged,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableBroadcastReceivers() {
        val bootComplete = ComponentName(context, BootCompleteReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            bootComplete,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val timeChanged = ComponentName(context, TimeChangedReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            timeChanged,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}
