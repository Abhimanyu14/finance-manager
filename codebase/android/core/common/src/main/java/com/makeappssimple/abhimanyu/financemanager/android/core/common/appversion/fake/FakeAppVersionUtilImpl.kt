package com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil

class FakeAppVersionUtilImpl : AppVersionUtil {
    override fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = "versionName",
            versionNumber = 12L,
        )
    }
}
