package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult

@Stable
class OpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>? = null,
    val openSourceLicensesBottomSheetType: OpenSourceLicensesBottomSheetType = OpenSourceLicensesBottomSheetType.NONE,
    val setOpenSourceLicensesBottomSheetType: (OpenSourceLicensesBottomSheetType) -> Unit = {},
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
    val resetBottomSheetType: () -> Unit = {
        setOpenSourceLicensesBottomSheetType(OpenSourceLicensesBottomSheetType.NONE)
    }
}

@Composable
fun rememberOpenSourceLicensesScreenUIState(
    data: MyResult<OpenSourceLicensesScreenUIData>?,
): OpenSourceLicensesScreenUIState {
    val (openSourceLicensesBottomSheetType: OpenSourceLicensesBottomSheetType, setOpenSourceLicensesBottomSheetType: (OpenSourceLicensesBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = OpenSourceLicensesBottomSheetType.NONE,
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