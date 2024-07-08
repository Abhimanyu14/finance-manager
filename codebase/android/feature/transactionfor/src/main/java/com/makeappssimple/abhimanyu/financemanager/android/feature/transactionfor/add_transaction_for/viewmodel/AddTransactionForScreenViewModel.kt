package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionForScreenViewModel @Inject constructor(
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val titleTextFieldErrorTextStringResourceId: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private val screenBottomSheetType: MutableStateFlow<AddTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionForScreenBottomSheetType.None,
        )
    // endregion

    // region observables
    private val isCtaButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<AddTransactionForScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddTransactionForScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllTransactionForValues()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
        observeForIsCtaButtonEnabled()
    }
    // endregion

    // region getAllTransactionForValues
    private fun getAllTransactionForValues() {
        viewModelScope.launch {
            allTransactionForValues = getAllTransactionForValuesUseCase()
        }
    }
    // endregion

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                title,
                isCtaButtonEnabled,
                titleTextFieldErrorTextStringResourceId,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                        isCtaButtonEnabled,
                        titleTextFieldErrorTextStringResourceId,
                    ),
                ->
                uiStateAndStateEvents.update {
                    AddTransactionForScreenUIStateAndStateEvents(
                        state = AddTransactionForScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
                            isLoading = allTransactionForValues.isEmpty(),
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            title = title,
                            titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
                        ),
                        events = AddTransactionForScreenUIStateEvents(
                            insertTransactionFor = ::insertTransactionFor,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setTitle = ::setTitle,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region observeForIsCtaButtonEnabled
    private fun observeForIsCtaButtonEnabled() {
        viewModelScope.launch {
            title.collectLatest { title ->
                titleTextFieldErrorTextStringResourceId.update {
                    null
                }
                val updatedIsCtaButtonEnabled = if (title.text.isBlank()) {
                    false
                } else if (
                    allTransactionForValues.find {
                        it.title.equalsIgnoringCase(
                            other = title.text.trim(),
                        )
                    }.isNotNull()
                ) {
                    titleTextFieldErrorTextStringResourceId.update {
                        R.string.screen_add_or_edit_transaction_for_error_exists
                    }
                    false
                } else {
                    true
                }
                isCtaButtonEnabled.update {
                    updatedIsCtaButtonEnabled
                }
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun insertTransactionFor(
        transactionFor: TransactionFor,
    ) {
        viewModelScope.launch {
            insertTransactionForUseCase(transactionFor)
            navigator.navigateUp()
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddTransactionForScreenBottomSheetType = AddTransactionForScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedAddTransactionForScreenBottomSheetType: AddTransactionForScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionForScreenBottomSheetType
        }
    }

    private fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }
    // endregion
}
