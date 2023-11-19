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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.MyTimePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_account.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_account.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_account.SelectAccountBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

enum class AddOrEditTransactionScreenBottomSheetType : ScreenBottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_ACCOUNT_FROM,
    SELECT_ACCOUNT_TO,
}

@Composable
internal fun AddOrEditTransactionScreenUI(
    uiState: AddOrEditTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AddOrEditTransactionScreenUIEvent) -> Unit = {},
) {
    val clearFocus = {
        state.focusManager.clearFocus()
    }

    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            state.focusRequester.requestFocus()
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionScreenBottomSheetType.NONE,
        screenBottomSheetType = uiState.addOrEditTransactionBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION)
            .fillMaxSize(),
        sheetContent = {
            when (uiState.addOrEditTransactionBottomSheetType) {
                AddOrEditTransactionScreenBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionScreenBottomSheetType.SELECT_CATEGORY -> {
                    SelectCategoryBottomSheet(
                        data = SelectCategoryBottomSheetData(
                            filteredCategories = uiState.filteredCategories,
                            selectedCategoryId = uiState.uiState.category?.id,
                        ),
                        events = SelectCategoryBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateCategory = { updatedCategory ->
                                handleUIEvents(
                                    AddOrEditTransactionScreenUIEvent.UpdateCategory(
                                        updatedCategory = updatedCategory,
                                    )
                                )
                            },
                        ),
                    )
                }

                AddOrEditTransactionScreenBottomSheetType.SELECT_ACCOUNT_FROM -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.uiState.accountFrom?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateAccount = { updatedAccount ->
                                handleUIEvents(
                                    AddOrEditTransactionScreenUIEvent.UpdateAccountFrom(
                                        updatedAccountFrom = updatedAccount,
                                    )
                                )
                            },
                        ),
                    )
                }

                AddOrEditTransactionScreenBottomSheetType.SELECT_ACCOUNT_TO -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.uiState.accountTo?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateAccount = { updatedAccount ->
                                handleUIEvents(
                                    AddOrEditTransactionScreenUIEvent.UpdateAccountTo(
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
                    handleUIEvents(AddOrEditTransactionScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = {
            clearFocus()
        },
        isModalBottomSheetVisible = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionScreenBottomSheetType.NONE,
        backHandlerEnabled = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionScreenBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
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
                    handleUIEvents(
                        AddOrEditTransactionScreenUIEvent.UpdateTransactionDate(
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
                    handleUIEvents(
                        AddOrEditTransactionScreenUIEvent.UpdateTransactionTime(
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
                .testTag(SCREEN_ADD_OR_EDIT_TRANSACTION)
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
                            handleUIEvents(
                                AddOrEditTransactionScreenUIEvent.UpdateSelectedTransactionTypeIndex(
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
                        handleUIEvents(AddOrEditTransactionScreenUIEvent.ClearAmount)
                    },
                    onValueChange = { updatedAmount ->
                        handleUIEvents(
                            AddOrEditTransactionScreenUIEvent.UpdateAmount(
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
                            uiState.setAddOrEditTransactionBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SELECT_CATEGORY
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
                            handleUIEvents(AddOrEditTransactionScreenUIEvent.ClearTitle)
                        },
                        onValueChange = { updatedTitle ->
                            handleUIEvents(
                                AddOrEditTransactionScreenUIEvent.UpdateTitle(
                                    updatedTitle = updatedTitle,
                                ),
                            )
                        },
                    ),
                )
            }
            AnimatedVisibility(
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
                            handleUIEvents(
                                AddOrEditTransactionScreenUIEvent.UpdateTitle(
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
                            handleUIEvents(
                                AddOrEditTransactionScreenUIEvent.UpdateSelectedTransactionForIndex(
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
                            handleUIEvents(AddOrEditTransactionScreenUIEvent.ClearDescription)
                        },
                        onValueChange = { updatedDescription ->
                            handleUIEvents(
                                AddOrEditTransactionScreenUIEvent.UpdateDescription(
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
                            uiState.setAddOrEditTransactionBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SELECT_ACCOUNT_FROM
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
                            uiState.setAddOrEditTransactionBottomSheetType(
                                AddOrEditTransactionScreenBottomSheetType.SELECT_ACCOUNT_TO
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
                        handleUIEvents(AddOrEditTransactionScreenUIEvent.OnCtaButtonClick)
                    },
                ),
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
