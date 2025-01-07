package com.makeappssimple.abhimanyu.financemanager.android.core.logger

private object MyLoggerConstants {
    const val DEFAULT_LOGGER_TAG = "Abhi"
}

public interface MyLogger {
    public fun logInfo(
        message: String,
        tag: String = MyLoggerConstants.DEFAULT_LOGGER_TAG,
    )
}
