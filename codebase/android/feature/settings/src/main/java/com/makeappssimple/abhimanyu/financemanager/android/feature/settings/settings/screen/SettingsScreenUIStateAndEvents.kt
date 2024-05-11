package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class SettingsScreenUIStateAndEvents(
    val state: SettingsScreenUIState,
    val events: SettingsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Immutable
internal data class SettingsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
)

@Composable
internal fun rememberSettingsScreenUIStateAndEvents(
    data: MyResult<SettingsScreenUIData>?,
): SettingsScreenUIStateAndEvents {
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    var screenBottomSheetType: SettingsScreenBottomSheetType by remember {
        mutableStateOf(
            value = SettingsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType ->
            screenBottomSheetType = updatedSettingsScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        snackbarHostState,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: SettingsScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        SettingsScreenUIStateAndEvents(
            state = SettingsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                snackbarHostState = snackbarHostState,
                isLoading = unwrappedData.isNull() || unwrappedData.isLoading,
                isReminderEnabled = unwrappedData?.isReminderEnabled,
                appVersion = unwrappedData?.appVersion,
            ),
            events = SettingsScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(SettingsScreenBottomSheetType.None)
                },
            ),
        )
    }
}
