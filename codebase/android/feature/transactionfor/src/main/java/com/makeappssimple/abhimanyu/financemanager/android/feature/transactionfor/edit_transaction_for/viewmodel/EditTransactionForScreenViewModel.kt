package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.usecase.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.EditTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditTransactionForScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val editTransactionForScreenDataValidationUseCase: EditTransactionForScreenDataValidationUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val navigator: Navigator,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
) : ScreenViewModel() {
    // region screen args
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    private var currentTransactionFor: TransactionFor? = null
    // endregion

    // region UI state
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<EditTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionForScreenBottomSheetType.None,
        )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<EditTransactionForScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditTransactionForScreenUIStateAndStateEvents(),
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
            getCurrentTransactionFor()
            processCurrentTransactionFor()
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
                uiStateAndStateEvents.update {
                    EditTransactionForScreenUIStateAndStateEvents(
                        state = EditTransactionForScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
                            isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                            isLoading = isLoading,
                            screenBottomSheetType = screenBottomSheetType,
                            titleError = validationState.titleError,
                            title = title,
                        ),
                        events = EditTransactionForScreenUIStateEvents(
                            clearTitle = ::clearTitle,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setTitle = ::setTitle,
                            updateTransactionFor = ::updateTransactionFor,
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

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditTransactionForScreenBottomSheetType = EditTransactionForScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedEditTransactionForScreenBottomSheetType: EditTransactionForScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditTransactionForScreenBottomSheetType
        }
    }

    private fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun updateTransactionFor() {
        val currentTransactionForValue = currentTransactionFor ?: return
        val uiState = uiStateAndStateEvents.value.state
        viewModelScope.launch {
            val isTransactionForUpdated = updateTransactionForUseCase(
                currentTransactionFor = currentTransactionForValue,
                title = uiState.title.text,
            )
            if (isTransactionForUpdated) {
                navigator.navigateUp()
            } else {
                // TODO(Abhi): Show error
            }
        }
    }
    // endregion
}
