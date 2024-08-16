package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

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
) : ScreenViewModel(),
    AddTransactionForScreenUIStateDelegate by AddTransactionForScreenUIStateDelegateImpl(
        insertTransactionForUseCase = insertTransactionForUseCase,
        navigator = navigator,
    ) {
    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()
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
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region getAllTransactionForValues
    private suspend fun getAllTransactionForValues() {
        allTransactionForValues = getAllTransactionForValuesUseCase()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
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
                            insertTransactionFor = {
                                insertTransactionFor(
                                    uiState = uiStateAndStateEvents.value.state,
                                    coroutineScope = viewModelScope,
                                )
                            },
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
}
