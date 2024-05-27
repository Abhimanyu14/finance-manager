package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class OpenSourceLicensesScreenUIStateAndEvents(
    val state: OpenSourceLicensesScreenUIState,
    val events: OpenSourceLicensesScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberOpenSourceLicensesScreenUIStateAndEvents(
): OpenSourceLicensesScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType by remember {
        mutableStateOf(
            value = OpenSourceLicensesScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedOpenSourceLicensesScreenBottomSheetType: OpenSourceLicensesScreenBottomSheetType ->
            screenBottomSheetType = updatedOpenSourceLicensesScreenBottomSheetType
        }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        OpenSourceLicensesScreenUIStateAndEvents(
            state = OpenSourceLicensesScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
            ),
            events = OpenSourceLicensesScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(OpenSourceLicensesScreenBottomSheetType.None)
                },
            )
        )
    }
}
