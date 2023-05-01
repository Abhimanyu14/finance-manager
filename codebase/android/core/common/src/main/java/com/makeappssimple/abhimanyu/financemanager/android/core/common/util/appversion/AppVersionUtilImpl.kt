package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.appversion

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat

class AppVersionUtilImpl(
    private val context: Context,
) : AppVersionUtil {
    override fun getAppVersion(): AppVersion? {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                packageManager.getPackageInfo(packageName, 0)
            }
            AppVersion(
                versionName = packageInfo.versionName,
                versionNumber = PackageInfoCompat.getLongVersionCode(packageInfo),
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            null
        }
    }
}
