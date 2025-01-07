package com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.cre.BuildConfig
import javax.inject.Inject

public class BuildConfigUtilImpl @Inject constructor() : BuildConfigUtil {
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
