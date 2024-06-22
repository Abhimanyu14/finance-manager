package com.makeappssimple.abhimanyu.financemanager.android.core.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@AndroidEntryPoint
public class BootCompleteReceiver : BroadcastReceiver() {
    private val job = Job()
    private val coroutineScope = MainScope() + job

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
            coroutineScope.launch {
                doWork()
                cleanUp()
            }
        }
    }

    private suspend fun doWork() {
        val reminder = myPreferencesRepository.getReminder() ?: return
        if (reminder.isEnabled.orFalse()) {
            alarmKit.enableReminder()
        }
    }

    private fun cleanUp() {
        job.cancel()
    }
}
