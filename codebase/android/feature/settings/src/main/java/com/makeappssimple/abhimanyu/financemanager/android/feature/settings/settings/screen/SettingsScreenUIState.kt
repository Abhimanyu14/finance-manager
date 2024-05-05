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
public class SettingsScreenUIState(
    public val isLoading: Boolean,
    public val isReminderEnabled: Boolean?,
    public val screenBottomSheetType: SettingsScreenBottomSheetType,
    public val snackbarHostState: SnackbarHostState,
    public val appVersion: String?,
    public val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
public fun rememberSettingsScreenUIState(
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
