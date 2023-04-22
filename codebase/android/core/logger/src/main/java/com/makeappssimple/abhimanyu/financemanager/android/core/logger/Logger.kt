package com.makeappssimple.abhimanyu.financemanager.android.core.logger

const val defaultLoggerTag = "Abhi"

interface Logger {
    fun logError(
        tag: String = defaultLoggerTag,
        message: String,
    )
}
