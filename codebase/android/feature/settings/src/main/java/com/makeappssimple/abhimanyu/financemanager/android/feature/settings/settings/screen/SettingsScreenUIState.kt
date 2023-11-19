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
    val screenBottomSheetType: SettingsScreenBottomSheetType = SettingsScreenBottomSheetType.NONE,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val setScreenBottomSheetType: (SettingsScreenBottomSheetType) -> Unit = {},
    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    val isReminderEnabled: Boolean? = unwrappedData?.isReminderEnabled,
    val appVersion: String? = unwrappedData?.appVersion,
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(SettingsScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberSettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>?,
): SettingsScreenUIState {
    val (screenBottomSheetType: SettingsScreenBottomSheetType, setScreenBottomSheetType: (SettingsScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = SettingsScreenBottomSheetType.NONE,
        )
    }
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    return remember(
        data,
        screenBottomSheetType,
        snackbarHostState,
        setScreenBottomSheetType,
    ) {
        SettingsScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            snackbarHostState = snackbarHostState,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
