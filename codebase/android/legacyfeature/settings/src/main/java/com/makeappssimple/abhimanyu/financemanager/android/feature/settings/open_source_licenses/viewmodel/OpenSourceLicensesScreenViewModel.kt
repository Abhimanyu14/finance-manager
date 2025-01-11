package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class OpenSourceLicensesScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    @VisibleForTesting internal val navigator: Navigator,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), OpenSourceLicensesScreenUIStateDelegate by OpenSourceLicensesScreenUIStateDelegateImpl(
    navigator = navigator,
) {
    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<OpenSourceLicensesScreenUIState> =
        MutableStateFlow(
            value = OpenSourceLicensesScreenUIState(),
        )
    internal val uiStateEvents: OpenSourceLicensesScreenUIStateEvents =
        OpenSourceLicensesScreenUIStateEvents(
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            setScreenBottomSheetType = ::setScreenBottomSheetType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {}

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                    ),
                ->
                uiState.update {
                    OpenSourceLicensesScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != OpenSourceLicensesScreenBottomSheetType.None,
                        isLoading = isLoading,
                        screenBottomSheetType = screenBottomSheetType,
                    )
                }
            }
        }
    }
    // endregion
}
