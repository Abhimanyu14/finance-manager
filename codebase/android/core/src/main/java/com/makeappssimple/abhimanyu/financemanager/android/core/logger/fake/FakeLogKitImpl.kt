package com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit

public class FakeLogKitImpl : LogKit {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
