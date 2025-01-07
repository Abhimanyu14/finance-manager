package com.makeappssimple.abhimanyu.financemanager.android.cre.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.BuildConfigUtil

public class MyLoggerImpl(
    private val buildConfigUtil: BuildConfigUtil,
) : MyLogger {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
        if (buildConfigUtil.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
