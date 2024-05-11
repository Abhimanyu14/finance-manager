package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class OpenSourceLicensesScreenUIStateAndEvents(
    val state: OpenSourceLicensesScreenUIState,
    val events: OpenSourceLicensesScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class OpenSourceLicensesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
)

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
        val unwrappedData: OpenSourceLicensesScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
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
