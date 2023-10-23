package com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger

class FakeMyLoggerImpl : MyLogger {
    override fun logError(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
