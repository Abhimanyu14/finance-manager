package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class OpenSourceLicensesScreenUIStateAndEvents(
    val state: OpenSourceLicensesScreenUIState,
    val events: OpenSourceLicensesScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class OpenSourceLicensesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberOpenSourceLicensesScreenUIStateAndEvents(
    data: MyResult<OpenSourceLicensesScreenUIData>?,
): OpenSourceLicensesScreenUIStateAndEvents {
    var screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType by remember {
        mutableStateOf(
            value = OpenSourceLicensesScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedOpenSourceLicensesScreenBottomSheetType: OpenSourceLicensesScreenBottomSheetType ->
            screenBottomSheetType = updatedOpenSourceLicensesScreenBottomSheetType
        }

    return remember(
        data,
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
