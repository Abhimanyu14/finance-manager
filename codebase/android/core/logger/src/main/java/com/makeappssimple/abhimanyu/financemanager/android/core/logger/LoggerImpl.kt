package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.isDebugBuild

class LoggerImpl : Logger {

    override fun logError(
        tag: String,
        message: String,
    ) {
        if (isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
