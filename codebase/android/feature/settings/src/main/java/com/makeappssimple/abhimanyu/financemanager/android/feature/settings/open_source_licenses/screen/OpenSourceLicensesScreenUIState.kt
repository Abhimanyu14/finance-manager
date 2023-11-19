package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
class OpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>? = null,
    private val unwrappedData: OpenSourceLicensesScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.NONE,
    val setScreenBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit = {},
    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(OpenSourceLicensesScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberOpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>?,
): OpenSourceLicensesScreenUIState {
    val (screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType, setScreenBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = OpenSourceLicensesScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        OpenSourceLicensesScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
