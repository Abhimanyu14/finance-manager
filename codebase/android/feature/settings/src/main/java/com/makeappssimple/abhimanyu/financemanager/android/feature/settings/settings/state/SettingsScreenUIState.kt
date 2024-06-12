package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType

@Stable
internal data class SettingsScreenUIState(
    val isLoading: Boolean,
    val isReminderEnabled: Boolean?,
    val screenBottomSheetType: SettingsScreenBottomSheetType,
    val snackbarHostState: SnackbarHostState,
    val appVersion: String?,
) : ScreenUIState
