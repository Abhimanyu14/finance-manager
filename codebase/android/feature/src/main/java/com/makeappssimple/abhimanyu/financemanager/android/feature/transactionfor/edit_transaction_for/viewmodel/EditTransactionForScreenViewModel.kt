package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.usecase.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.EditTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditTransactionForScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val editTransactionForScreenDataValidationUseCase: EditTransactionForScreenDataValidationUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val navigationKit: NavigationKit,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), EditTransactionForScreenUIStateDelegate by EditTransactionForScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    navigationKit = navigationKit,
    updateTransactionForUseCase = updateTransactionForUseCase,
) {
    // region screen args
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<EditTransactionForScreenUIState> =
        MutableStateFlow(
            value = EditTransactionForScreenUIState(),
        )
    internal val uiStateEvents: EditTransactionForScreenUIStateEvents =
        EditTransactionForScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            setScreenBottomSheetType = ::setScreenBottomSheetType,
            setTitle = ::setTitle,
            updateTransactionFor = {
                updateTransactionFor(
                    uiState = uiState.value
                )
            },
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                getAllTransactionForValues()
                getCurrentTransactionFor()
                processCurrentTransactionFor()
            }
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

    // region getOriginalTransactionFor
    private suspend fun getCurrentTransactionFor() {
        val transactionForId = screenArgs.transactionForId ?: return
        currentTransactionFor = getTransactionForUseCase(
            id = transactionForId,
        )
    }
    // endregion

    // region processCurrentTransactionFor
    private fun processCurrentTransactionFor() {
        val currentTransactionForValue = currentTransactionFor ?: return
        setTitle(
            updatedTitle = title.value
                .copy(
                    text = currentTransactionForValue.title,
                    selection = TextRange(
                        index = currentTransactionForValue.title.length,
                    ),
                ),
        )
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
                val validationState = editTransactionForScreenDataValidationUseCase(
                    allTransactionForValues = allTransactionForValues,
                    currentTransactionFor = currentTransactionFor,
                    enteredTitle = title.text,
                )
                uiState.update {
                    EditTransactionForScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
                        isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                        isLoading = isLoading,
                        screenBottomSheetType = screenBottomSheetType,
                        titleError = validationState.titleError,
                        title = title,
                    )
                }
            }
        }
    }
    // endregion
}
