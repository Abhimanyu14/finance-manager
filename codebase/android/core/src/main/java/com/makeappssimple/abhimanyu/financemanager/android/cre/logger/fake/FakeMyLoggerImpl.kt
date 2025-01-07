package com.makeappssimple.abhimanyu.financemanager.android.cre.logger.fake

import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.MyLogger

public class FakeMyLoggerImpl : MyLogger {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
