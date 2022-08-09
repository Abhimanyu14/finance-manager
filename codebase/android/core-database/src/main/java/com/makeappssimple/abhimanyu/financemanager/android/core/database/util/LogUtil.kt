package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import android.util.Log

fun logError(
    message: String,
) {
    if (isDebugBuild()) {
        Log.e("Abhi", message)
    }
}
