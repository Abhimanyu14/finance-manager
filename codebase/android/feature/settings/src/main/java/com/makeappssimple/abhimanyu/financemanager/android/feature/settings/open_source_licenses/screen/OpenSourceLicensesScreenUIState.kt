package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

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
public class OpenSourceLicensesScreenUIState(
    public val screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.None,
    public val setScreenBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit = {},
    public val isLoading: Boolean,
    public val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
public fun rememberOpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>?,
): OpenSourceLicensesScreenUIState {
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

        OpenSourceLicensesScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull() || unwrappedData.isLoading,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(OpenSourceLicensesScreenBottomSheetType.None)
            },
        )
    }
}
