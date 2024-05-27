package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class SettingsScreenUIStateAndEvents(
    val state: SettingsScreenUIState,
    val events: SettingsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberSettingsScreenUIStateAndEvents(
    isLoading: Boolean,
    reminder: Reminder?,
    appVersionName: String,
): SettingsScreenUIStateAndEvents {
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    // region screen bottom sheet type
    var screenBottomSheetType: SettingsScreenBottomSheetType by remember {
        mutableStateOf(
            value = SettingsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType ->
            screenBottomSheetType = updatedSettingsScreenBottomSheetType
        }
    // endregion

    return remember(
        snackbarHostState,
        screenBottomSheetType,
        setScreenBottomSheetType,
        isLoading,
        reminder,
        appVersionName,
    ) {
        SettingsScreenUIStateAndEvents(
            state = SettingsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                snackbarHostState = snackbarHostState,
                isLoading = isLoading,
                isReminderEnabled = reminder?.isEnabled.orFalse(),
                appVersion = appVersionName,
            ),
            events = SettingsScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(SettingsScreenBottomSheetType.None)
                },
            ),
        )
    }
}
