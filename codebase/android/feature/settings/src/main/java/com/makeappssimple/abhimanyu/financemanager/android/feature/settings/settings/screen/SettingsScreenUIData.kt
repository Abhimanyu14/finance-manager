package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
data class SettingsScreenUIData(
    val isReminderEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val appVersion: String? = null,
) : ScreenUIData
