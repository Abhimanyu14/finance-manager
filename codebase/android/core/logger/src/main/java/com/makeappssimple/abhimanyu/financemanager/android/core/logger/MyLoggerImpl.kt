package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil

public class MyLoggerImpl(
    private val buildConfigUtil: BuildConfigUtil,
) : MyLogger {
    override fun logError(
        message: String,
        tag: String,
    ) {
        if (buildConfigUtil.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
