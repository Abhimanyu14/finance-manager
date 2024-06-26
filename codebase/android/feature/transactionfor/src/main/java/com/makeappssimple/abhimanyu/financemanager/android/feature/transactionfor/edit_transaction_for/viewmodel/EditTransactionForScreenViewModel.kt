package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.EditTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditTransactionForScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val navigator: Navigator,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private val transactionForValues: MutableList<TransactionFor> = mutableListOf()
    private var transactionFor: TransactionFor? = null

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val screenBottomSheetType: MutableStateFlow<EditTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionForScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<EditTransactionForScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditTransactionForScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllTransactionForValues()
            getOriginalTransactionFor()

            setTitle(
                title.value.copy(
                    text = transactionFor?.title.orEmpty(),
                    selection = TextRange(transactionFor?.title.orEmpty().length),
                )
            )

            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

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
                var titleTextFieldErrorTextStringResourceId: Int? = null
                val isCtaButtonEnabled = if (title.text.isBlank()) {
                    false
                } else if (title.text != transactionFor?.title && transactionForValues.find { transactionForValue ->
                        transactionForValue.title.equalsIgnoringCase(
                            other = title.text,
                        )
                    }.isNotNull()
                ) {
                    titleTextFieldErrorTextStringResourceId =
                        R.string.screen_add_or_edit_transaction_for_error_exists
                    false
                } else {
                    true
                }

                uiStateAndStateEvents.update {
                    EditTransactionForScreenUIStateAndStateEvents(
                        state = EditTransactionForScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
                            isLoading = false,
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            appBarTitleTextStringResourceId = R.string.screen_edit_transaction_for_appbar_title,
                            ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_for_floating_action_button_content_description,
                            title = title,
                            titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
                        ),
                        events = EditTransactionForScreenUIStateEvents(
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

    private suspend fun getAllTransactionForValues() {
        transactionForValues.addAll(getAllTransactionForValuesUseCase())
    }

    private suspend fun getOriginalTransactionFor() {
        screenArgs.originalTransactionForId?.let { id ->
            transactionFor = getTransactionForUseCase(
                id = id,
            )
        }
    }

    // region state events
    private fun updateTransactionFor(
        title: String,
    ) {
        val updatedTransactionFor = transactionFor?.copy(
            title = title,
        ) ?: return
        viewModelScope.launch {
            updateTransactionForValuesUseCase(
                updatedTransactionFor,
            )
            navigator.navigateUp()
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
    // endregion
}
