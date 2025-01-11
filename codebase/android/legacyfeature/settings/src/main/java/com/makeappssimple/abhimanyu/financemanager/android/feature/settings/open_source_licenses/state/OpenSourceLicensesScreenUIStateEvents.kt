package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType

@Stable
internal class OpenSourceLicensesScreenUIStateEvents(
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (OpenSourceLicensesScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents
