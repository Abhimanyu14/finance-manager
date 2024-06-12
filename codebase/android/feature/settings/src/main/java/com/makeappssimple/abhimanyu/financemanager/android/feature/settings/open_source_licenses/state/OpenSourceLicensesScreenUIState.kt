package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType

@Stable
internal data class OpenSourceLicensesScreenUIState(
    val screenBottomSheetType: OpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
) : ScreenUIState
