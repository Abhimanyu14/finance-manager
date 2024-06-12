package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

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
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType

@Stable
internal class SettingsScreenUIStateAndStateEvents(
    val state: SettingsScreenUIState,
    val events: SettingsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberSettingsScreenUIStateAndEvents(
    isLoading: Boolean,
    reminder: Reminder?,
    appVersionName: String,
): SettingsScreenUIStateAndStateEvents {
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
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(SettingsScreenBottomSheetType.None)
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
        SettingsScreenUIStateAndStateEvents(
            state = SettingsScreenUIState(
                isLoading = isLoading,
                isReminderEnabled = reminder?.isEnabled.orFalse(),
                screenBottomSheetType = screenBottomSheetType,
                snackbarHostState = snackbarHostState,
                appVersion = appVersionName,
            ),
            events = SettingsScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
            ),
        )
    }
}
