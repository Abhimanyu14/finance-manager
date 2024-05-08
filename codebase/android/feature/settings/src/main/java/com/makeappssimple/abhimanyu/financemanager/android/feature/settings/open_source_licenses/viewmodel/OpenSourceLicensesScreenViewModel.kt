package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface OpenSourceLicensesScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<OpenSourceLicensesScreenUIData>?>

    public fun handleUIEvent(
        uiEvent: OpenSourceLicensesScreenUIEvent,
    )
}
