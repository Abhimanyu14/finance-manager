package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Immutable
internal data class SettingsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents
