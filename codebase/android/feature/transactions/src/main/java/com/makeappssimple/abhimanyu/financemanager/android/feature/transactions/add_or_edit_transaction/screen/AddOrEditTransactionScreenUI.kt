package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.SelectAccountBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.SelectCategoryBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import kotlinx.coroutines.delay

@Composable
internal fun AddOrEditTransactionScreenUI(
    uiState: AddOrEditTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AddOrEditTransactionScreenUIEvent) -> Unit = {},
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
        showModalBottomSheet = uiState.screenBottomSheetType != AddOrEditTransactionScreenBottomSheetType.None,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AddOrEditTransactionScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is AddOrEditTransactionScreenBottomSheetType.SelectCategory -> {
                    SelectCategoryBottomSheet(
                        data = SelectCategoryBottomSheetData(
                            filteredCategories = uiState.filteredCategories,
                            selectedCategoryId = uiState.uiState.category?.id,
                        ),
                        events = SelectCategoryBottomSheetEvents(
                            resetBottomSheetType = uiState.resetScreenBottomSheetType,
                            updateCategory = { updatedCategory ->
                                handleUIEvent(
                                    AddOrEditTransactionScreenUIEvent.OnCategoryUpdated(
                                        updatedCategory = updatedCategory,
                                    )
                                )
                            },
                        ),
                    )
                }

                is AddOrEditTransactionScreenBottomSheetType.SelectAccountFrom -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.uiState.accountFrom?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetScreenBottomSheetType,
                            updateAccount = { updatedAccount ->
                                handleUIEvent(
                                    AddOrEditTransactionScreenUIEvent.OnAccountFromUpdated(
                                        updatedAccountFrom = updatedAccount,
                                    )
                                )
                            },
                        ),
                    )
                }

                is AddOrEditTransactionScreenBottomSheetType.SelectAccountTo -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.uiState.accountTo?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetScreenBottomSheetType,
                            updateAccount = { updatedAccount ->
                                handleUIEvent(
                                    AddOrEditTransactionScreenUIEvent.OnAccountToUpdated(
                                        updatedAccountTo = updatedAccount,
                                    )
                                )
                            },
                        ),
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = {
                    handleUIEvent(AddOrEditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != AddOrEditTransactionScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != AddOrEditTransactionScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        MyDatePicker(
            data = MyDatePickerData(
                isVisible = uiState.isTransactionDatePickerDialogVisible,
                endLocalDate = uiState.currentLocalDate,
                selectedLocalDate = uiState.uiState.transactionDate,
            ),
            events = MyDatePickerEvents(
                onNegativeButtonClick = {
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                },
                onPositiveButtonClick = { updatedTransactionDate ->
                    handleUIEvent(
                        AddOrEditTransactionScreenUIEvent.OnTransactionDateUpdated(
                            updatedTransactionDate = updatedTransactionDate,
                        )
                    )
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                },
            )
        )
        MyTimePicker(
            data = MyTimePickerData(
                isVisible = uiState.isTransactionTimePickerDialogVisible,
                selectedLocalDate = uiState.uiState.transactionTime,
            ),
            events = MyTimePickerEvents(
                onNegativeButtonClick = {
                    uiState.setIsTransactionTimePickerDialogVisible(false)
                },
                onPositiveButtonClick = { updatedTransactionTime ->
                    handleUIEvent(
                        AddOrEditTransactionScreenUIEvent.OnTransactionTimeUpdated(
                            updatedTransactionTime = updatedTransactionTime,
                        )
                    )
                    uiState.setIsTransactionTimePickerDialogVisible(false)
                },
            ),
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
                        selectedItemIndex = uiState.uiState.selectedTransactionTypeIndex,
                    ),
                    events = MyHorizontalScrollingRadioGroupEvents(
                        onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                            handleUIEvent(
                                AddOrEditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated(
                                    updatedSelectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
                                )
                            )
                        },
                    ),
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
                    textFieldValue = uiState.uiState.amount,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_amount,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_amount,
                    supportingText = if (uiState.uiState.amountErrorText.isNotNullOrBlank()) {
                        {
                            AnimatedVisibility(
                                uiState.uiState.amountErrorText.isNotNullOrBlank(),
                            ) {
                                MyText(
                                    text = stringResource(
                                        id = R.string.screen_add_or_edit_transaction_amount_error_text,
                                        uiState.uiState.amountErrorText,
                                    ),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.error,
                                    ),
                                )
                            }
                        }
                    } else {
                        null
                    },
                    isError = uiState.uiState.amountErrorText.isNotNull(),
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
                events = MyOutlinedTextFieldEvents(
                    onClickTrailingIcon = {
                        handleUIEvent(AddOrEditTransactionScreenUIEvent.OnClearAmountButtonClick)
                    },
                    onValueChange = { updatedAmount ->
                        handleUIEvent(
                            AddOrEditTransactionScreenUIEvent.OnAmountUpdated(
                                updatedAmount = updatedAmount,
                            )
                        )
                    },
                ),
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
                        value = uiState.uiState.category?.title.orEmpty(),
                        labelTextStringResourceId = R.string.screen_add_or_edit_transaction_category,
                    ),
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            clearFocus()
                            uiState.setScreenBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SelectCategory
                            )
                        },
                    ),
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
                        textFieldValue = uiState.uiState.title,
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
                    events = MyOutlinedTextFieldEvents(
                        onClickTrailingIcon = {
                            handleUIEvent(AddOrEditTransactionScreenUIEvent.OnClearTitleButtonClick)
                        },
                        onValueChange = { updatedTitle ->
                            handleUIEvent(
                                AddOrEditTransactionScreenUIEvent.OnTitleUpdated(
                                    updatedTitle = updatedTitle,
                                ),
                            )
                        },
                    ),
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
                    events = MyHorizontalScrollingSelectionGroupEvents(
                        onSelectionChange = { index ->
                            clearFocus()
                            handleUIEvent(
                                AddOrEditTransactionScreenUIEvent.OnTitleUpdated(
                                    updatedTitle = uiState.uiState.title.copy(
                                        text = uiState.titleSuggestions[index],
                                    ),
                                ),
                            )
                        },
                    ),
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
                        selectedItemIndex = uiState.uiState.selectedTransactionForIndex,
                    ),
                    events = MyHorizontalScrollingRadioGroupEvents(
                        onSelectionChange = { updatedSelectedTransactionForIndex ->
                            clearFocus()
                            handleUIEvent(
                                AddOrEditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated(
                                    updatedSelectedTransactionForIndex = updatedSelectedTransactionForIndex,
                                )
                            )
                        },
                    ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isDescriptionTextFieldVisible,
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
                        textFieldValue = uiState.uiState.description,
                        labelTextStringResourceId = R.string.screen_add_or_edit_transaction_description,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_description,
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
                    events = MyOutlinedTextFieldEvents(
                        onClickTrailingIcon = {
                            handleUIEvent(AddOrEditTransactionScreenUIEvent.OnClearDescriptionButtonClick)
                        },
                        onValueChange = { updatedDescription ->
                            handleUIEvent(
                                AddOrEditTransactionScreenUIEvent.OnDescriptionUpdated(
                                    updatedDescription = updatedDescription,
                                )
                            )
                        },
                    ),
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
                        value = uiState.uiState.accountFrom?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountFromTextFieldLabelTextStringResourceId,
                    ),
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            clearFocus()
                            uiState.setScreenBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SelectAccountFrom
                            )
                        },
                    ),
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
                        value = uiState.uiState.accountTo?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountToTextFieldLabelTextStringResourceId,
                    ),
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            clearFocus()
                            uiState.setScreenBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SelectAccountTo
                            )
                        },
                    ),
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
                    value = uiState.uiState.transactionDate.formattedDate(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_date,
                ),
                events = MyReadOnlyTextFieldEvents(
                    onClick = {
                        clearFocus()
                        uiState.setIsTransactionDatePickerDialogVisible(true)
                    },
                ),
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
                    value = uiState.uiState.transactionTime.formattedTime(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_time,
                ),
                events = MyReadOnlyTextFieldEvents(
                    onClick = {
                        clearFocus()
                        uiState.setIsTransactionTimePickerDialogVisible(true)
                    },
                ),
            )
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                events = SaveButtonEvents(
                    onClick = {
                        handleUIEvent(AddOrEditTransactionScreenUIEvent.OnCtaButtonClick)
                    },
                ),
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
