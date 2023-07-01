package com.makeappssimple.abhimanyu.financemanager.android.core.logger

private const val DEFAULT_LOGGER_TAG = "Abhi"

interface MyLogger {
    fun logError(
        tag: String = DEFAULT_LOGGER_TAG,
        message: String,
    )
}
