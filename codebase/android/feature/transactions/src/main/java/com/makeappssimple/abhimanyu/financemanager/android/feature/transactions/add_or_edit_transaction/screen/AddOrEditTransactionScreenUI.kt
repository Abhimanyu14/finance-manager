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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyTimePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source.SelectAccountBottomSheetEvents
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import java.time.LocalDate
import java.time.LocalTime

enum class AddOrEditTransactionBottomSheetType : BottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_SOURCE_FROM,
    SELECT_SOURCE_TO,
}

@Immutable
data class AddOrEditTransactionScreenUIData(
    val uiState: AddOrEditTransactionScreenUiStateData = AddOrEditTransactionScreenUiStateData(),
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState = AddOrEditTransactionScreenUiVisibilityState.Expense,
    val isCtaButtonEnabled: Boolean = false,
    val filteredCategories: List<Category> = emptyList(),
    val sources: List<Source> = emptyList(),
    val titleSuggestions: List<String> = emptyList(),
    val transactionTypesForNewTransaction: List<TransactionType> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val selectedTransactionType: TransactionType? = null,
)

@Immutable
internal data class AddOrEditTransactionScreenUIEvents(
    val clearAmount: () -> Unit,
    val clearDescription: () -> Unit,
    val clearTitle: () -> Unit,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateAmount: (updatedAmount: TextFieldValue) -> Unit,
    val updateCategory: (updatedCategory: Category?) -> Unit,
    val updateDescription: (updatedDescription: TextFieldValue) -> Unit,
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
    val updateAccountFrom: (updatedAccountFrom: Source?) -> Unit,
    val updateAccountTo: (updatedAccountTo: Source?) -> Unit,
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit,
    val updateTransactionDate: (updatedTransactionDate: LocalDate) -> Unit,
    val updateTransactionTime: (updatedTransactionTime: LocalTime) -> Unit,
)

@Composable
internal fun AddOrEditTransactionScreenUI(
    events: AddOrEditTransactionScreenUIEvents,
    uiState: AddOrEditTransactionScreenUIState,
    state: CommonScreenUIState,
) {
    val transactionTimePickerDialog = getMyTimePickerDialog(
        context = state.context,
        currentTime = uiState.uiState.transactionTime,
        onTimeSetListener = {
            events.updateTransactionTime(it)
        },
    )
    val clearFocus = {
        state.focusManager.clearFocus()
    }

    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            state.focusRequester.requestFocus()
            state.keyboardController?.show()
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        bottomSheetType = uiState.addOrEditTransactionBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.addOrEditTransactionBottomSheetType) {
                AddOrEditTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionBottomSheetType.SELECT_CATEGORY -> {
                    SelectCategoryBottomSheet(
                        data = SelectCategoryBottomSheetData(
                            filteredCategories = uiState.filteredCategories,
                            selectedCategoryId = uiState.uiState.category?.id,
                        ),
                        events = SelectCategoryBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateCategory = { updatedCategory ->
                                events.updateCategory(updatedCategory)
                            },
                        ),
                    )
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.sources,
                            selectedAccountId = uiState.uiState.sourceFrom?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateAccount = { updatedAccount ->
                                events.updateAccountFrom(updatedAccount)
                            },
                        ),
                    )
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.sources,
                            selectedAccountId = uiState.uiState.sourceTo?.id,
                        ),
                        events = SelectAccountBottomSheetEvents(
                            resetBottomSheetType = uiState.resetBottomSheetType,
                            updateAccount = { updatedSource ->
                                events.updateAccountTo(updatedSource)
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
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            clearFocus()
        },
        isModalBottomSheetVisible = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        backHandlerEnabled = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
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
                onPositiveButtonClick = {
                    events.updateTransactionDate(it)
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                },
                onNegativeButtonClick = {
                    uiState.setIsTransactionDatePickerDialogVisible(false)
                },
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                            events.updateSelectedTransactionTypeIndex(
                                updatedSelectedTransactionTypeIndex
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
                    onClickTrailingIcon = events.clearAmount,
                    onValueChange = events.updateAmount,
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
                                AddOrEditTransactionBottomSheetType.SELECT_CATEGORY
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
                        onClickTrailingIcon = events.clearTitle,
                        onValueChange = events.updateTitle,
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
                            events.updateTitle(
                                uiState.uiState.title.copy(
                                    text = uiState.titleSuggestions[index],
                                )
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
                            events.updateSelectedTransactionForIndex(
                                updatedSelectedTransactionForIndex
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
                        onClickTrailingIcon = events.clearDescription,
                        onValueChange = events.updateDescription,
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
                        value = uiState.uiState.sourceFrom?.name.orEmpty(),
                        labelTextStringResourceId = uiState.sourceFromTextFieldLabelTextStringResourceId,
                    ),
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            clearFocus()
                            uiState.setAddOrEditTransactionBottomSheetType(
                                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM
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
                        value = uiState.uiState.sourceTo?.name.orEmpty(),
                        labelTextStringResourceId = uiState.sourceToTextFieldLabelTextStringResourceId,
                    ),
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            clearFocus()
                            uiState.setAddOrEditTransactionBottomSheetType(
                                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO
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
                        transactionTimePickerDialog.show()
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
                    onClick = events.onCtaButtonClick,
                ),
            )
            NavigationBarSpacer()
        }
    }
}
