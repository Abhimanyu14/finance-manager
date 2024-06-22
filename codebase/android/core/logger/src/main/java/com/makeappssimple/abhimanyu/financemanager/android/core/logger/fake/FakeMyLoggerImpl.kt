package com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger

public class FakeMyLoggerImpl : MyLogger {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
