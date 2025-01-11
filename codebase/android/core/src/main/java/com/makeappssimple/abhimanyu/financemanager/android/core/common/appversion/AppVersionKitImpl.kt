package com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat

public class AppVersionKitImpl(
    private val context: Context,
) : AppVersionKit {
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
            nameNotFoundException: NameNotFoundException,
        ) {
            nameNotFoundException.printStackTrace()
            null
        } catch (
            unsupportedOperationException: UnsupportedOperationException,
        ) {
            unsupportedOperationException.printStackTrace()
            null
        }
    }
}
