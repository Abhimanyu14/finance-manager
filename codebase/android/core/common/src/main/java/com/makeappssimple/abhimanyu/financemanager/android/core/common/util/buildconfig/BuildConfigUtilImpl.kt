package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.core.common.BuildConfig

class BuildConfigUtilImpl : BuildConfigUtil {
    override fun isDebugBuild(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getBuildVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    override fun isAndroidApiEqualToOrAbove(buildVersionNumber: Int): Boolean {
        return getBuildVersion() >= buildVersionNumber
    }
}
