package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult

@Stable
class SettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>?,
    val settingsBottomSheetType: SettingsBottomSheetType,
    val setSettingsBottomSheetType: (SettingsBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading
    val appVersion: String? = unwrappedData?.appVersion
    val resetBottomSheetType: () -> Unit = {
        setSettingsBottomSheetType(SettingsBottomSheetType.NONE)
    }
}

@Composable
fun rememberSettingsScreenUIState(
    data: MyResult<SettingsScreenUIData>?,
): SettingsScreenUIState {
    val (settingsBottomSheetType: SettingsBottomSheetType, setSettingsBottomSheetType: (SettingsBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = SettingsBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        settingsBottomSheetType,
        setSettingsBottomSheetType,
    ) {
        SettingsScreenUIState(
            data = data,
            settingsBottomSheetType = settingsBottomSheetType,
            setSettingsBottomSheetType = setSettingsBottomSheetType,
        )
    }
}
