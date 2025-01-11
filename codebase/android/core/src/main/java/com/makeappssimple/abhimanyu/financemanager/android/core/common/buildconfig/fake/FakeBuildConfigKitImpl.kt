package com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.fake

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.core.BuildConfig
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigKit
import javax.inject.Inject

public class FakeBuildConfigKitImpl @Inject constructor() : BuildConfigKit {
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
