package com.makeappssimple.abhimanyu.financemanager.android.core.testing.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger

class TestMyLoggerImpl(
    private val buildConfigUtil: BuildConfigUtil,
) : MyLogger {
    override fun logError(
        tag: String,
        message: String,
    ) {
        Log.e(tag, message)
    }
}
