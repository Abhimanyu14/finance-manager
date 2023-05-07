package com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig

interface BuildConfigUtil {
    fun isDebugBuild(): Boolean

    fun getBuildVersion(): Int

    fun isAndroidApiEqualToOrAbove(
        buildVersionNumber: Int,
    ): Boolean
}
