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
    data: MyResult<OpenSourceLicensesScreenUIData>? = null,
    private val unwrappedData: OpenSourceLicensesScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.None,
    public val setScreenBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit = {},
    public val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(OpenSourceLicensesScreenBottomSheetType.None)
    },
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
        OpenSourceLicensesScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
