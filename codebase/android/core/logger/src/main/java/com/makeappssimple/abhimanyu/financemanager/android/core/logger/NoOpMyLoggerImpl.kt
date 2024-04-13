package com.makeappssimple.abhimanyu.financemanager.android.core.logger

public class NoOpMyLoggerImpl : MyLogger {
    override fun logError(
        message: String,
        tag: String,
    ) {
    }
}
