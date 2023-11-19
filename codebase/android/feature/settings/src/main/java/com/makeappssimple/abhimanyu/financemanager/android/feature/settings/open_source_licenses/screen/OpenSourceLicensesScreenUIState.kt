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
    val openSourceLicensesBottomSheetType: OpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.NONE,
    val setOpenSourceLicensesBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit = {},
    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    val resetBottomSheetType: () -> Unit = {
        setOpenSourceLicensesBottomSheetType(OpenSourceLicensesScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberOpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>?,
): OpenSourceLicensesScreenUIState {
    val (openSourceLicensesBottomSheetType: OpenSourceLicensesScreenBottomSheetType, setOpenSourceLicensesBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = OpenSourceLicensesScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        openSourceLicensesBottomSheetType,
        setOpenSourceLicensesBottomSheetType,
    ) {
        OpenSourceLicensesScreenUIState(
            data = data,
            openSourceLicensesBottomSheetType = openSourceLicensesBottomSheetType,
            setOpenSourceLicensesBottomSheetType = setOpenSourceLicensesBottomSheetType,
        )
    }
}
