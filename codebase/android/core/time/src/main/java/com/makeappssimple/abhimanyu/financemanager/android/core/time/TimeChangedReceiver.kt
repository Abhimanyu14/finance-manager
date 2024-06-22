package com.makeappssimple.abhimanyu.financemanager.android.core.time

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@AndroidEntryPoint
public class TimeChangedReceiver : BroadcastReceiver() {
    private val job = Job()
    private val coroutineScope = MainScope() + job

    @Inject
    public lateinit var alarmKit: AlarmKit

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == "android.intent.action.TIME_SET") {
            coroutineScope.launch {
                doWork()
                cleanUp()
            }
        }
    }

    private suspend fun doWork() {
        alarmKit.enableReminder()
    }

    private fun cleanUp() {
        job.cancel()
    }
}
