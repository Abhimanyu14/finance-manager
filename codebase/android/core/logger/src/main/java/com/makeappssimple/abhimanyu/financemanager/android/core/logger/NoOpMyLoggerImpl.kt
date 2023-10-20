package com.makeappssimple.abhimanyu.financemanager.android.core.logger

class NoOpMyLoggerImpl : MyLogger {
    override fun logError(
        message: String,
        tag: String,
    ) {
    }
}
