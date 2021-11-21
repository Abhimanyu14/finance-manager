package com.makeappssimple.abhimanyu.financemanager.android.utils

import android.util.Log

fun logError(
    message: String,
) {
    if (isDebugBuild()) {
        Log.e("Abhi", message)
    }
}
