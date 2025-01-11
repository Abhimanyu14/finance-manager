package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigKit

public class LogKitImpl(
    private val buildConfigKit: BuildConfigKit,
) : LogKit {
    override fun logInfo(
        message: String,
        tag: String,
    ) {
        if (buildConfigKit.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
