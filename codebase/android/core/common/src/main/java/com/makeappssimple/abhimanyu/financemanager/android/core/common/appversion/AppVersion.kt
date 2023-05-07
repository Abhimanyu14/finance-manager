package com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion

import androidx.compose.runtime.Immutable

@Immutable
data class AppVersion(
    val versionName: String,
    val versionNumber: Long,
)
