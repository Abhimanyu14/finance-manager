package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
class SettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>? = null,
    private val unwrappedData: SettingsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val settingsBottomSheetType: SettingsBottomSheetType = SettingsBottomSheetType.NONE,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val setSettingsBottomSheetType: (SettingsBottomSheetType) -> Unit = {},
    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    val isReminderEnabled: Boolean? = unwrappedData?.isReminderEnabled,
    val appVersion: String? = unwrappedData?.appVersion,
    val resetBottomSheetType: () -> Unit = {
        setSettingsBottomSheetType(SettingsBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberSettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>?,
): SettingsScreenUIState {
    val (settingsBottomSheetType: SettingsBottomSheetType, setSettingsBottomSheetType: (SettingsBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = SettingsBottomSheetType.NONE,
        )
    }
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    return remember(
        data,
        settingsBottomSheetType,
        snackbarHostState,
        setSettingsBottomSheetType,
    ) {
        SettingsScreenUIState(
            data = data,
            settingsBottomSheetType = settingsBottomSheetType,
            snackbarHostState = snackbarHostState,
            setSettingsBottomSheetType = setSettingsBottomSheetType,
        )
    }
}
