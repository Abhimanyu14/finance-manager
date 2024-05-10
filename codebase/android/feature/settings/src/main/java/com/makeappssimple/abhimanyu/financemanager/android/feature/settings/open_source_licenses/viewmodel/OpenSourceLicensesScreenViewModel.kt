package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class OpenSourceLicensesScreenViewModel @Inject constructor(
    closeableCoroutineScope: CloseableCoroutineScope,
    @VisibleForTesting internal val navigator: Navigator,
) : ScreenViewModel, ViewModel(closeableCoroutineScope) {
    public val screenUIData: StateFlow<MyResult<OpenSourceLicensesScreenUIData>?> =
        MutableStateFlow(
            value = MyResult.Success(
                data = OpenSourceLicensesScreenUIData(),
            )
        ).defaultObjectStateIn(
            scope = closeableCoroutineScope,
        )

    public fun handleUIEvent(
        uiEvent: OpenSourceLicensesScreenUIEvent,
    ) {
        when (uiEvent) {
            is OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                navigateUp()
            }
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }
}
