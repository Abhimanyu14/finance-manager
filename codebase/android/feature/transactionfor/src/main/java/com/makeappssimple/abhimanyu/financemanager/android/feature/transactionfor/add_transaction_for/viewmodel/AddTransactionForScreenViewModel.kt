package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.usecase.AddTransactionForScreenDataValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionForScreenViewModel @Inject constructor(
    private val addTransactionForScreenDataValidationUseCase: AddTransactionForScreenDataValidationUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val insertTransactionForUseCase: InsertTransactionForUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<AddTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionForScreenBottomSheetType.None,
        )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
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
            startLoading()
            getAllTransactionForValues()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }
    // endregion

    // region getAllTransactionForValues
    private suspend fun getAllTransactionForValues() {
        allTransactionForValues = getAllTransactionForValuesUseCase()
    }
    // endregion

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                title,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                    ),
                ->
                val validationState = addTransactionForScreenDataValidationUseCase(
                    allTransactionForValues = allTransactionForValues,
                    enteredTitle = title.text,
                )
                uiStateAndStateEvents.update {
                    AddTransactionForScreenUIStateAndStateEvents(
                        state = AddTransactionForScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            titleError = validationState.titleError,
                            isBottomSheetVisible = screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
                            isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                            isLoading = isLoading,
                            title = title,
                        ),
                        events = AddTransactionForScreenUIStateEvents(
                            clearTitle = ::clearTitle,
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
    private fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    private fun insertTransactionFor() {
        val uiState = uiStateAndStateEvents.value.state
        viewModelScope.launch {
            val isTransactionForInserted = insertTransactionForUseCase(
                title = uiState.title?.text.orEmpty(),
            )
            if (isTransactionForInserted == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigator.navigateUp()
            }
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
