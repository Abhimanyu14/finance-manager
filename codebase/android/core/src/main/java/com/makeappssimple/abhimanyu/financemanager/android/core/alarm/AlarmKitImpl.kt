package com.makeappssimple.abhimanyu.financemanager.android.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.makeappssimple.abhimanyu.financemanager.android.core.boot.BootCompletedReceiver
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.time.TimeChangedReceiver
import java.time.LocalTime

public class AlarmKitImpl(
    private val context: Context,
    private val dateTimeKit: DateTimeKit,
    private val logKit: LogKit,
    private val myPreferencesRepository: MyPreferencesRepository,
) : AlarmKit {
    override suspend fun cancelReminderAlarm(): Boolean {
        var isAlarmCancelled = cancelAlarm()
        if (!isAlarmCancelled) {
            return false
        }

        disableBroadcastReceivers()

        isAlarmCancelled = updateIsReminderEnabledInPreferences(
            isReminderEnabled = false,
        )

        if (isAlarmCancelled) {
            logKit.logInfo(
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

        isAlarmSet = updateIsReminderEnabledInPreferences(
            isReminderEnabled = true,
        )

        if (isAlarmSet) {
            logKit.logInfo(
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
            ComponentName(context, BootCompletedReceiver::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableBootCompleteReceiver() {
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, BootCompletedReceiver::class.java),
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
    private suspend fun updateIsReminderEnabledInPreferences(
        isReminderEnabled: Boolean,
    ): Boolean {
        return myPreferencesRepository.updateIsReminderEnabled(
            isReminderEnabled = isReminderEnabled,
        )
    }
    // endregion

    // region date time
    private fun getInitialAlarmTimestamp(
        reminder: Reminder,
    ): Long {
        return dateTimeKit.getTimestamp(
            time = LocalTime.of(reminder.hour, reminder.min),
        )
    }
    // endregion
}
