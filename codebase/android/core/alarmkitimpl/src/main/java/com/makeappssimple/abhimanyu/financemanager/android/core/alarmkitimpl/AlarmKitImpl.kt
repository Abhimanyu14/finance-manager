package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.boot.BootCompleteReceiver
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.time.TimeChangedReceiver
import java.time.LocalTime

public class AlarmKitImpl(
    private val context: Context,
    private val dateTimeUtil: DateTimeUtil,
    private val myLogger: MyLogger,
    private val myPreferencesRepository: MyPreferencesRepository,
) : AlarmKit {
    override suspend fun cancelReminderAlarm(): Boolean {
        var isAlarmCancelled = cancelAlarm()
        if (!isAlarmCancelled) {
            return false
        }

        disableBroadcastReceivers()

        isAlarmCancelled = setIsReminderEnabledInPreferences(
            isReminderEnabled = false,
        )

        if (isAlarmCancelled) {
            myLogger.logInfo(
                message = "Alarm cancelled",
            )
        }
        return isAlarmCancelled
    }

    override suspend fun setReminderAlarm(): Boolean {
        val reminder = myPreferencesRepository.getReminder() ?: return false
        val initialAlarmTimestamp = getInitialAlarmTimestamp(
            reminder = reminder,
        )
        var isAlarmSet = setAlarm(
            initialAlarmTimestamp = initialAlarmTimestamp,
        )
        if (!isAlarmSet) {
            return false
        }

        enableBroadcastReceivers()

        isAlarmSet = setIsReminderEnabledInPreferences(
            isReminderEnabled = true,
        )

        if (isAlarmSet) {
            myLogger.logInfo(
                message = "Alarm set for : ${reminder.hour}:${reminder.min}",
            )
        }
        return isAlarmSet
    }

    // region alarm
    private fun setAlarm(
        initialAlarmTimestamp: Long,
    ): Boolean {
        val alarmManager = getAlarmManager() ?: return false
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            getAlarmReceiverIntent(),
            PendingIntent.FLAG_IMMUTABLE,
        )
        alarmManager.setRepeating(
            AlarmManager.RTC,
            initialAlarmTimestamp,
            AlarmManager.INTERVAL_DAY,
            pendingIntent,
        )
        return true
    }

    private fun cancelAlarm(): Boolean {
        val alarmManager = getAlarmManager() ?: return false
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            getAlarmReceiverIntent(),
            PendingIntent.FLAG_IMMUTABLE,
        )
        alarmManager.cancel(pendingIntent)
        return true
    }

    private fun getAlarmManager(): AlarmManager? {
        return context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    private fun getAlarmReceiverIntent(): Intent {
        return Intent(context, AlarmReceiver::class.java)
    }
    // endregion

    // region broadcast receivers
    private fun enableBroadcastReceivers() {
        enableBootCompleteReceiver()
        enableTimeChangedReceiver()
    }

    private fun disableBroadcastReceivers() {
        disableBootCompleteReceiver()
        disableTimeChangedReceiver()
    }

    private fun enableBootCompleteReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, BootCompleteReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableBootCompleteReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, BootCompleteReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun enableTimeChangedReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, TimeChangedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableTimeChangedReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, TimeChangedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    // endregion

    // region preferences
    private suspend fun setIsReminderEnabledInPreferences(
        isReminderEnabled: Boolean,
    ): Boolean {
        return myPreferencesRepository.setIsReminderEnabled(
            isReminderEnabled = isReminderEnabled,
        )
    }
    // endregion

    // region date time
    private fun getInitialAlarmTimestamp(
        reminder: Reminder,
    ): Long {
        return dateTimeUtil.getTimestamp(
            time = LocalTime.of(reminder.hour, reminder.min),
        )
    }
    // endregion
}
