package com.makeappssimple.abhimanyu.financemanager.android.core.logger

private object MyLoggerConstant {
    const val DEFAULT_LOGGER_TAG = "Abhi"
}

public interface MyLogger {
    public fun logError(
        message: String,
        tag: String = MyLoggerConstant.DEFAULT_LOGGER_TAG,
    )
}
