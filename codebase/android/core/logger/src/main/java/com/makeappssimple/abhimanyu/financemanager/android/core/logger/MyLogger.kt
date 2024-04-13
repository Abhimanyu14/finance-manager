package com.makeappssimple.abhimanyu.financemanager.android.core.logger

private const val DEFAULT_LOGGER_TAG = "Abhi"

public interface MyLogger {
    public fun logError(
        message: String,
        tag: String = DEFAULT_LOGGER_TAG,
    )
}
