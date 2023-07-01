package com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger

class FakeMyLoggerImpl : MyLogger {
    override fun logError(
        tag: String,
        message: String,
    ) {
        Log.e(tag, message)
    }
}
