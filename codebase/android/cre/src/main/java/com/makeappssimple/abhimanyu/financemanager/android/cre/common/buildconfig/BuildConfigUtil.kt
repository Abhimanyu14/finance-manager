package com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig

public interface BuildConfigUtil {
    public fun isDebugBuild(): Boolean

    public fun getBuildVersion(): Int

    public fun isAndroidApiEqualToOrAbove(
        buildVersionNumber: Int,
    ): Boolean
}
