package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
internal class SettingsScreenUIState(
    val isLoading: Boolean,
    val isReminderEnabled: Boolean?,
    val screenBottomSheetType: SettingsScreenBottomSheetType,
    val snackbarHostState: SnackbarHostState,
    val appVersion: String?,
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
internal fun rememberSettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>?,
): SettingsScreenUIState {
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
        SettingsScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            snackbarHostState = snackbarHostState,
            isLoading = unwrappedData.isNull() || unwrappedData.isLoading,
            isReminderEnabled = unwrappedData?.isReminderEnabled,
            appVersion = unwrappedData?.appVersion,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(SettingsScreenBottomSheetType.None)
            },
        )
    }
}
