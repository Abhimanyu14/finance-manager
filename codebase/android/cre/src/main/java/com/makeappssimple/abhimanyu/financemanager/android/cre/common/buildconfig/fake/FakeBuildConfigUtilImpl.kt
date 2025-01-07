package com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.fake

import android.os.Build
import com.makeappssimple.abhimanyu.financemanager.android.cre.BuildConfig
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.BuildConfigUtil
import javax.inject.Inject

public class FakeBuildConfigUtilImpl @Inject constructor() : BuildConfigUtil {
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
