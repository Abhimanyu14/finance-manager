package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class OpenSourceLicensesScreenViewModel @Inject constructor(
    @VisibleForTesting internal val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<OpenSourceLicensesScreenBottomSheetType> =
        MutableStateFlow(
            value = OpenSourceLicensesScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<OpenSourceLicensesScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = OpenSourceLicensesScreenUIStateAndStateEvents(),
        )
    // endregion

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
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
                uiStateAndStateEvents.update {
                    OpenSourceLicensesScreenUIStateAndStateEvents(
                        state = OpenSourceLicensesScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != OpenSourceLicensesScreenBottomSheetType.None,
                            isLoading = isLoading,
                        ),
                        events = OpenSourceLicensesScreenUIStateEvents(
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region state events
    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedOpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedOpenSourceLicensesScreenBottomSheetType: OpenSourceLicensesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedOpenSourceLicensesScreenBottomSheetType
        }
    }
    // endregion
}
