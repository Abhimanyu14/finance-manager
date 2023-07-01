package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil

class MyLoggerImpl(
    private val buildConfigUtil: BuildConfigUtil,
) : MyLogger {
    override fun logError(
        tag: String,
        message: String,
    ) {
        if (buildConfigUtil.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
