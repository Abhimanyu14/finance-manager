package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType

@Stable
internal data class SettingsScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val isReminderEnabled: Boolean? = null,
    val screenBottomSheetType: SettingsScreenBottomSheetType = SettingsScreenBottomSheetType.None,
    val appVersion: String? = null,
) : ScreenUIState
