package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Stable
class SettingsScreenUIState(
    data: SettingsScreenUIData,
    val settingsBottomSheetType: SettingsBottomSheetType,
    val setSettingsBottomSheetType: (SettingsBottomSheetType) -> Unit,
) {
    val isLoading: Boolean = data.isLoading
    val appVersion: String? = data.appVersion
    val resetBottomSheetType: () -> Unit = {
        setSettingsBottomSheetType(SettingsBottomSheetType.NONE)
    }
}

@Composable
fun rememberSettingsScreenUIState(
    data: SettingsScreenUIData,
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
