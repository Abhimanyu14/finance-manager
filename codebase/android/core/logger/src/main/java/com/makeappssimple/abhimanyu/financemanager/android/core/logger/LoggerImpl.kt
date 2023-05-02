package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.BuildConfigUtil

class LoggerImpl(
    private val buildConfigUtil: BuildConfigUtil,
) : Logger {
    override fun logError(
        tag: String,
        message: String,
    ) {
        if (buildConfigUtil.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
