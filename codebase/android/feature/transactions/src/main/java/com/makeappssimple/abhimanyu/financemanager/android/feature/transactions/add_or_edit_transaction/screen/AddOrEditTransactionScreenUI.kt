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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyDatePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyTimePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category.SelectCategoryBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source.SelectSourceBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextField
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
    val currentTimeMillis: Long = 0L,
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
    val updateSourceFrom: (updatedSourceFrom: Source?) -> Unit,
    val updateSourceTo: (updatedSourceTo: Source?) -> Unit,
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
    val transactionDatePickerDialog = getMyDatePickerDialog(
        context = state.context,
        selectedDate = uiState.uiState.transactionDate,
        currentTimeMillis = uiState.currentTimeMillis,
        onDateSetListener = {
            events.updateTransactionDate(it)
        },
    )
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

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
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
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.addOrEditTransactionBottomSheetType) {
                AddOrEditTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionBottomSheetType.SELECT_CATEGORY -> {
                    SelectCategoryBottomSheetContent(
                        filteredCategories = uiState.filteredCategories,
                        selectedCategoryId = uiState.uiState.category?.id,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                    ) { updatedCategory ->
                        events.updateCategory(updatedCategory)
                    }
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    SelectSourceBottomSheetContent(
                        sources = uiState.sources,
                        selectedSourceId = uiState.uiState.sourceFrom?.id,
                        resetBottomSheetType = uiState.resetBottomSheetType
                    ) { updatedSource ->
                        events.updateSourceFrom(updatedSource)
                    }
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    SelectSourceBottomSheetContent(
                        sources = uiState.sources,
                        selectedSourceId = uiState.uiState.sourceTo?.id,
                        resetBottomSheetType = uiState.resetBottomSheetType
                    ) { updatedSource ->
                        events.updateSourceTo(updatedSource)
                    }
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            clearFocus()
        },
        backHandlerEnabled = uiState.addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionTypesRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    items = uiState.transactionTypesForNewTransactionChipUIData,
                    selectedItemIndex = uiState.uiState.selectedTransactionTypeIndex,
                    onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                        events.updateSelectedTransactionTypeIndex(
                            updatedSelectedTransactionTypeIndex
                        )
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            MyOutlinedTextField(
                textFieldValue = uiState.uiState.amount,
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_amount,
                trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_amount,
                onClickTrailingIcon = {
                    events.clearAmount()
                },
                onValueChange = { updatedAmount ->
                    events.updateAmount(updatedAmount)
                },
                supportingText = {
                    AnimatedVisibility(
                        uiState.uiState.amountErrorText.isNotNullOrBlank(),
                    ) {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_add_or_edit_transaction_amount_error_text,
                                uiState.uiState.amountErrorText.orEmpty(),
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.error,
                            ),
                        )
                    }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isCategoryTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = uiState.uiState.category?.title.orEmpty(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_category,
                    onClick = {
                        clearFocus()
                        uiState.setAddOrEditTransactionBottomSheetType(
                            AddOrEditTransactionBottomSheetType.SELECT_CATEGORY
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTitleTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = uiState.uiState.title,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_title,
                    onClickTrailingIcon = {
                        events.clearTitle()
                    },
                    onValueChange = { updatedTitle ->
                        events.updateTitle(updatedTitle)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            clearFocus()
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTitleSuggestionsVisible && uiState.titleSuggestionsChipUIData.isNotEmpty(),
            ) {
                MyHorizontalScrollingSelectionGroup(
                    items = uiState.titleSuggestionsChipUIData,
                    onSelectionChange = { index ->
                        clearFocus()
                        events.updateTitle(
                            uiState.uiState.title.copy(
                                text = uiState.titleSuggestions[index],
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionForRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    items = uiState.transactionForValuesChipUIData,
                    selectedItemIndex = uiState.uiState.selectedTransactionForIndex,
                    onSelectionChange = { updatedSelectedTransactionForIndex ->
                        clearFocus()
                        events.updateSelectedTransactionForIndex(
                            updatedSelectedTransactionForIndex
                        )
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isDescriptionTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = uiState.uiState.description,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_description,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_description,
                    onClickTrailingIcon = {
                        events.clearDescription()
                    },
                    onValueChange = { updatedDescription ->
                        events.updateDescription(updatedDescription)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            clearFocus()
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isSourceFromTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = uiState.uiState.sourceFrom?.name.orEmpty(),
                    labelTextStringResourceId = uiState.sourceFromTextFieldLabelTextStringResourceId,
                    onClick = {
                        clearFocus()
                        uiState.setAddOrEditTransactionBottomSheetType(
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isSourceToTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = uiState.uiState.sourceTo?.name.orEmpty(),
                    labelTextStringResourceId = uiState.sourceToTextFieldLabelTextStringResourceId,
                    onClick = {
                        clearFocus()
                        uiState.setAddOrEditTransactionBottomSheetType(
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            MyReadOnlyTextField(
                value = uiState.uiState.transactionDate.formattedDate(),
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_date,
                onClick = {
                    clearFocus()
                    transactionDatePickerDialog.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            MyReadOnlyTextField(
                value = uiState.uiState.transactionTime.formattedTime(),
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_time,
                onClick = {
                    clearFocus()
                    transactionTimePickerDialog.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            SaveButton(
                textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                isEnabled = uiState.isCtaButtonEnabled,
                onClick = {
                    clearFocus()
                    events.onCtaButtonClick()
                },
            )
        }
    }
}