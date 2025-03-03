package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event.EditTransactionScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.stringResourceId
import kotlinx.coroutines.delay

@Composable
internal fun EditTransactionScreenUI(
    uiState: EditTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: EditTransactionScreenUIEvent) -> Unit = {},
) {
    val clearFocus = {
        state.focusManager.clearFocus()
    }

    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            delay(300) // Source - https://stackoverflow.com/a/72783456/9636037
            state.focusRequester.requestFocus()
        }
    }

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is EditTransactionScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is EditTransactionScreenBottomSheetType.SelectCategory -> {
                    SelectCategoryBottomSheet(
                        data = SelectCategoryBottomSheetData(
                            filteredCategories = uiState.filteredCategories,
                            selectedCategoryId = uiState.category?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectCategoryBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(EditTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectCategoryBottomSheetEvent.UpdateCategory -> {
                                    handleUIEvent(
                                        EditTransactionScreenUIEvent.OnCategoryUpdated(
                                            updatedCategory = event.updatedCategory,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is EditTransactionScreenBottomSheetType.SelectAccountFrom -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.accountFrom?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectAccountBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(EditTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectAccountBottomSheetEvent.UpdateAccount -> {
                                    handleUIEvent(
                                        EditTransactionScreenUIEvent.OnAccountFromUpdated(
                                            updatedAccountFrom = event.updatedAccount,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is EditTransactionScreenBottomSheetType.SelectAccountTo -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.accountTo?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectAccountBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(EditTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectAccountBottomSheetEvent.UpdateAccount -> {
                                    handleUIEvent(
                                        EditTransactionScreenUIEvent.OnAccountToUpdated(
                                            updatedAccountTo = event.updatedAccount,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_edit_transaction_appbar_title,
                navigationAction = {
                    handleUIEvent(EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(EditTransactionScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        MyDatePicker(
            data = MyDatePickerData(
                isVisible = uiState.isTransactionDatePickerDialogVisible,
                endLocalDate = uiState.currentLocalDate,
                selectedLocalDate = uiState.transactionDate,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyDatePickerEvent.OnNegativeButtonClick -> {
                        handleUIEvent(EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed)
                    }

                    is MyDatePickerEvent.OnPositiveButtonClick -> {
                        handleUIEvent(
                            EditTransactionScreenUIEvent.OnTransactionDateUpdated(
                                updatedTransactionDate = event.selectedDate,
                            )
                        )
                    }
                }
            },
        )
        MyTimePicker(
            data = MyTimePickerData(
                isVisible = uiState.isTransactionTimePickerDialogVisible,
                selectedLocalDate = uiState.transactionTime,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyTimePickerEvent.OnNegativeButtonClick -> {
                        handleUIEvent(EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed)
                    }

                    is MyTimePickerEvent.OnPositiveButtonClick -> {
                        handleUIEvent(
                            EditTransactionScreenUIEvent.OnTransactionTimeUpdated(
                                updatedTransactionTime = event.selectedTime,
                            )
                        )
                    }
                }
            },
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionTypesRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                    data = MyHorizontalScrollingRadioGroupData(
                        isLoading = uiState.isLoading,
                        loadingItemSize = 5,
                        items = uiState.transactionTypesForNewTransactionChipUIData,
                        selectedItemIndex = uiState.selectedTransactionTypeIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                                handleUIEvent(
                                    EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated(
                                        updatedSelectedTransactionTypeIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyOutlinedTextFieldData(
                    isLoading = uiState.isLoading,
                    textFieldValue = uiState.amount,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_amount,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_amount,
                    supportingText = if (uiState.amountErrorText.isNotNullOrBlank()) {
                        {
                            AnimatedVisibility(
                                uiState.amountErrorText.isNotNullOrBlank(),
                            ) {
                                MyText(
                                    text = stringResource(
                                        id = R.string.screen_add_or_edit_transaction_amount_error_text,
                                        uiState.amountErrorText,
                                    ),
                                    style = MaterialTheme.typography.bodySmall
                                        .copy(
                                            color = MaterialTheme.colorScheme.error,
                                        ),
                                )
                            }
                        }
                    } else {
                        null
                    },
                    isError = uiState.amountErrorText.isNotNull(),
                    visualTransformation = AmountCommaVisualTransformation(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            clearFocus()
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                            handleUIEvent(EditTransactionScreenUIEvent.OnClearAmountButtonClick)
                        }

                        is MyOutlinedTextFieldEvent.OnValueChange -> {
                            handleUIEvent(
                                EditTransactionScreenUIEvent.OnAmountUpdated(
                                    updatedAmount = event.updatedValue,
                                )
                            )
                        }
                    }
                },
            )
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isCategoryTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.category?.title.orEmpty(),
                        labelTextStringResourceId = R.string.screen_add_or_edit_transaction_category,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(EditTransactionScreenUIEvent.OnCategoryTextFieldClick)
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTitleTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.title,
                        labelTextStringResourceId = R.string.screen_add_or_edit_transaction_title,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_title,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(EditTransactionScreenUIEvent.OnClearTitleButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    EditTransactionScreenUIEvent.OnTitleUpdated(
                                        updatedTitle = event.updatedValue,
                                    ),
                                )
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                // TODO(Abhi): Move logic to UI state
                visible = uiState.uiVisibilityState.isTitleSuggestionsVisible && uiState.titleSuggestionsChipUIData.isNotEmpty(),
            ) {
                MyHorizontalScrollingSelectionGroup(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 4.dp,
                        ),
                    data = MyHorizontalScrollingSelectionGroupData(
                        isLoading = uiState.isLoading,
                        items = uiState.titleSuggestionsChipUIData,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingSelectionGroupEvent.OnSelectionChange -> {
                                clearFocus()
                                handleUIEvent(
                                    EditTransactionScreenUIEvent.OnTitleUpdated(
                                        updatedTitle = uiState.title
                                            .copy(
                                                text = uiState.titleSuggestions[event.index],
                                            ),
                                    ),
                                )
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionForRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 4.dp,
                            end = 16.dp,
                        ),
                    data = MyHorizontalScrollingRadioGroupData(
                        isLoading = uiState.isLoading,
                        loadingItemSize = 5,
                        items = uiState.transactionForValuesChipUIData,
                        selectedItemIndex = uiState.selectedTransactionForIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                                clearFocus()
                                handleUIEvent(
                                    EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated(
                                        updatedSelectedTransactionForIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isAccountFromTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.accountFrom?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountFromText.stringResourceId,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(EditTransactionScreenUIEvent.OnAccountFromTextFieldClick)
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isAccountToTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.accountTo?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountToText.stringResourceId,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(EditTransactionScreenUIEvent.OnAccountToTextFieldClick)
                            }
                        }
                    },
                )
            }
            MyReadOnlyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyReadOnlyTextFieldData(
                    isLoading = uiState.isLoading,
                    value = uiState.transactionDate.formattedDate(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyReadOnlyTextFieldEvent.OnClick -> {
                            clearFocus()
                            handleUIEvent(EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick)
                        }
                    }
                },
            )
            MyReadOnlyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyReadOnlyTextFieldData(
                    isLoading = uiState.isLoading,
                    value = uiState.transactionTime.formattedTime(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_time,
                ),
                handleEvent = { event ->
                    when (event) {
                        MyReadOnlyTextFieldEvent.OnClick -> {
                            clearFocus()
                            handleUIEvent(EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick)
                        }
                    }
                },
            )
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = R.string.screen_edit_transaction_floating_action_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(EditTransactionScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
