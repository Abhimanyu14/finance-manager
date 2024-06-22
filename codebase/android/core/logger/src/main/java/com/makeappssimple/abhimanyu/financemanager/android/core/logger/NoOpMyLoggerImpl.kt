package com.makeappssimple.abhimanyu.financemanager.android.core.logger

public class NoOpMyLoggerImpl : MyLogger {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
    }
}
