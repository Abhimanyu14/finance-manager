package com.makeappssimple.abhimanyu.financemanager.android.core.testing.appversion

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil

class TestAppVersionUtil : AppVersionUtil {
    private var appVersion: AppVersion? = AppVersion(
        versionName = "versionName",
        versionNumber = 1L,
    )

    override fun getAppVersion(): AppVersion? {
        return appVersion
    }

    fun setAppVersion(
        updatedAppVersion: AppVersion?,
    ) {
        appVersion = updatedAppVersion
    }
}
