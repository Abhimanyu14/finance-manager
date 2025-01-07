package com.makeappssimple.abhimanyu.financemanager.android.cre.common.appversion.fake

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.appversion.AppVersionUtil

public class FakeAppVersionUtilImpl : AppVersionUtil {
    override fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = "versionName",
            versionNumber = 12L,
        )
    }
}
