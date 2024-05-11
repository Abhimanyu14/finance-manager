package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
internal class SettingsScreenUIState(
    val isLoading: Boolean,
    val isReminderEnabled: Boolean?,
    val screenBottomSheetType: SettingsScreenBottomSheetType,
    val snackbarHostState: SnackbarHostState,
    val appVersion: String?,
) : ScreenUIState
