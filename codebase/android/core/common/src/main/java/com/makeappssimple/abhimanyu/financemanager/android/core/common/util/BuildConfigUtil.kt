package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.core.common.BuildConfig

fun isDebugBuild() = BuildConfig.DEBUG

fun getBuildVersion() = Build.VERSION.SDK_INT

fun isAndroidApiEqualToOrAbove(
    buildVersionNumber: Int,
): Boolean {
    return getBuildVersion() >= buildVersionNumber
}
