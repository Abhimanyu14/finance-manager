package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class OpenSourceLicensesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents
