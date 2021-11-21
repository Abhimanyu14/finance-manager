package com.makeappssimple.abhimanyu.financemanager.android.utils

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.BuildConfig

fun isDebugBuild() = BuildConfig.DEBUG

fun getBuildVersion() = Build.VERSION.SDK_INT

fun isAndroidApiEqualToOrAbove(
    buildVersionNumber: Int,
): Boolean {
    return getBuildVersion() >= buildVersionNumber
}
