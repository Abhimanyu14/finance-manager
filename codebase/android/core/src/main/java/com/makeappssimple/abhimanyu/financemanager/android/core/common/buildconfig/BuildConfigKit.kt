package com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig

public interface BuildConfigKit {
    public fun isDebugBuild(): Boolean

    public fun getBuildVersion(): Int

    public fun isAndroidApiEqualToOrAbove(
        buildVersionNumber: Int,
    ): Boolean
}
