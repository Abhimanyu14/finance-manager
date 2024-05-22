package com.makeappssimple.abhimanyu.financemanager.android.core.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
public class BootCompleteReceiver : BroadcastReceiver() {

    @Inject
    public lateinit var alarmKit: AlarmKit

    @Inject
    public lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    public lateinit var dateTimeUtil: DateTimeUtil

    @Inject
    public lateinit var myPreferencesRepository: MyPreferencesRepository

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            CoroutineScope(
                context = dispatcherProvider.main,
            ).launch {
                val reminder = myPreferencesRepository.getReminder() ?: Reminder()
                if (reminder.isEnabled.orFalse()) {
                    alarmKit.enableReminder()
                }
            }
        }
    }
}
