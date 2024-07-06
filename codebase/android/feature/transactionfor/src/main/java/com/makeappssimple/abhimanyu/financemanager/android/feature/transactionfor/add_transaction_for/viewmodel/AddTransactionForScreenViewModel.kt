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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionForScreenViewModel @Inject constructor(
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>> =
        MutableStateFlow(
            persistentListOf()
        )

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val screenBottomSheetType: MutableStateFlow<AddTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionForScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<AddTransactionForScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddTransactionForScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllTransactionForValues()
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
                transactionForValues,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                        transactionForValues,
                    ),
                ->
                var titleTextFieldErrorTextStringResourceId: Int? = null
                val isCtaButtonEnabled = if (title.text.isBlank()) {
                    false
                } else if (transactionForValues.find {
                        it.title.equalsIgnoringCase(
                            other = title.text.trim(),
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
                    AddTransactionForScreenUIStateAndStateEvents(
                        state = AddTransactionForScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
                            isLoading = transactionForValues.isEmpty(),
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            appBarTitleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
                            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
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

    private fun insertTransactionFor(
        transactionFor: TransactionFor,
    ) {
        viewModelScope.launch {
            insertTransactionForUseCase(transactionFor)
            navigator.navigateUp()
        }
    }

    private fun getAllTransactionForValues() {
        viewModelScope.launch {
            transactionForValues.update {
                getAllTransactionForValuesUseCase()
            }
        }
    }

    // region state events
    private fun navigateToAddTransactionForScreen() {
        navigator.navigateToAddTransactionForScreen()
    }

    private fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigator.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )
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
