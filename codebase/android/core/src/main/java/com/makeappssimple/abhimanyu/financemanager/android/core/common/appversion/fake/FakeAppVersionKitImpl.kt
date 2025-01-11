package com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionKit

public class FakeAppVersionKitImpl : AppVersionKit {
    override fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = "versionName",
            versionNumber = 12L,
        )
    }
}
