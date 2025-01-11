package com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit

public class NoOpLogKitImpl : LogKit {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
    }
}
