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
import kotlinx.coroutines.coroutineScope
import java.time.LocalTime

public class AlarmKitImpl(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeUtil: DateTimeUtil,
    private val myLogger: MyLogger,
    private val myPreferencesRepository: MyPreferencesRepository,
) : AlarmKit {
    // TODO(Abhi): Return status of alarm
    override suspend fun disableReminder() {
        myLogger.logInfo(
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

        coroutineScope {
            myPreferencesRepository.setIsReminderEnabled(
                isReminderEnabled = false,
            )
        }
    }

    // TODO(Abhi): Return status of alarm
    override suspend fun enableReminder() {
        val reminder = myPreferencesRepository.getReminder() ?: return
        if (!reminder.isEnabled) {
            return
        }
        val initialAlarmTimestamp = dateTimeUtil.getTimestamp(
            time = LocalTime.of(reminder.hour, reminder.min),
        )
        getAlarmManager()?.let { alarmManager ->
            setAlarm(
                alarmManager = alarmManager,
                initialAlarmTimestamp = initialAlarmTimestamp,
                reminder = reminder,
            )
        }
    }

    private fun getAlarmManager(): AlarmManager? {
        return context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    private suspend fun setAlarm(
        alarmManager: AlarmManager,
        initialAlarmTimestamp: Long,
        reminder: Reminder,
    ) {
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
        myLogger.logInfo(
            message = "Alarm set for : ${reminder.hour}:${reminder.min}",
        )

        enableBroadcastReceivers()
        myPreferencesRepository.setIsReminderEnabled(
            isReminderEnabled = true,
        )
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
