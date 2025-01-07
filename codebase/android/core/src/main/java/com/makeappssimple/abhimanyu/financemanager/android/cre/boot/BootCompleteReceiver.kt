package com.makeappssimple.abhimanyu.financemanager.android.cre.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
public class BootCompleteReceiver : BroadcastReceiver() {
    @Inject
    public lateinit var alarmKit: AlarmKit

    @Inject
    public lateinit var myPreferencesRepository: MyPreferencesRepository

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            runBlocking {
                doWork()
            }
        }
    }

    private suspend fun doWork() {
        val reminder = myPreferencesRepository.getReminder() ?: return
        if (reminder.isEnabled.orFalse()) {
            alarmKit.setReminderAlarm()
        }
    }
}
