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
public class SettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>? = null,
    private val unwrappedData: SettingsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val screenBottomSheetType: SettingsScreenBottomSheetType = SettingsScreenBottomSheetType.NONE,
    public val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    public val setScreenBottomSheetType: (SettingsScreenBottomSheetType) -> Unit = {},
    public val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    public val isReminderEnabled: Boolean? = unwrappedData?.isReminderEnabled,
    public val appVersion: String? = unwrappedData?.appVersion,
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(SettingsScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
public fun rememberSettingsScreenUIState(
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
