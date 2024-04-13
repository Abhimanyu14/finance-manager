package com.makeappssimple.abhimanyu.financemanager.android.core.testing.logger

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger

public class TestMyLogger : MyLogger {
    override fun logError(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
