package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.core.content.pm.PackageInfoCompat

@Immutable
data class AppVersion(
    val versionName: String,
    val versionNumber: Long,
)

fun getAppVersion(
    context: Context,
): AppVersion? {
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
