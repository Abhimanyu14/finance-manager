package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import java.time.LocalDate
import java.time.LocalTime

private enum class AddOrEditTransactionBottomSheetType : BottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_SOURCE_FROM,
    SELECT_SOURCE_TO,
}

@Immutable
internal data class AddOrEditTransactionScreenUIData(
    val uiState: AddOrEditTransactionScreenUiState,
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    val isCtaButtonEnabled: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val filteredCategories: List<Category>,
    val sources: List<Source>,
    val titleSuggestions: List<String>,
    val transactionTypesForNewTransaction: List<TransactionType>,
    val transactionForValues: List<TransactionFor>,
    val currentTimeMillis: Long,
    val selectedTransactionType: TransactionType?,
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
    data: AddOrEditTransactionScreenUIData,
    events: AddOrEditTransactionScreenUIEvents,
    state: CommonScreenUIState,
) {
    val transactionDatePickerDialog = getMyDatePickerDialog(
        context = state.context,
        selectedDate = data.uiState.transactionDate,
        currentTimeMillis = data.currentTimeMillis,
        onDateSetListener = {
            events.updateTransactionDate(it)
        },
    )
    val transactionTimePickerDialog = getMyTimePickerDialog(
        context = state.context,
        currentTime = data.uiState.transactionTime,
        onTimeSetListener = {
            events.updateTransactionTime(it)
        },
    )

    var addOrEditTransactionBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionBottomSheetType.NONE,
        )
    }
    val transactionTypesForNewTransactionChipUIData = remember(
        key1 = data.transactionTypesForNewTransaction,
    ) {
        data.transactionTypesForNewTransaction
            .map { transactionType ->
                ChipUIData(
                    text = transactionType.title,
                )
            }
    }
    val titleSuggestionsChipUIData = remember(
        key1 = data.titleSuggestions,
    ) {
        data.titleSuggestions
            .map { title ->
                ChipUIData(
                    text = title,
                )
            }
    }
    val transactionForValuesChipUIData = remember(
        key1 = data.transactionForValues,
    ) {
        data.transactionForValues
            .map { transactionFor ->
                ChipUIData(
                    text = transactionFor.titleToDisplay,
                )
            }
    }

    val clearFocus = {
        state.focusManager.clearFocus()
    }
    val resetBottomSheetType = {
        addOrEditTransactionBottomSheetType = AddOrEditTransactionBottomSheetType.NONE
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    BottomSheetHandler(
        showModalBottomSheet = addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        bottomSheetType = addOrEditTransactionBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addOrEditTransactionBottomSheetType) {
                AddOrEditTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionBottomSheetType.SELECT_CATEGORY -> {
                    SelectCategoryBottomSheetContent(
                        filteredCategories = data.filteredCategories,
                        selectedCategoryId = data.uiState.category?.id,
                        resetBottomSheetType = resetBottomSheetType,
                    ) { updatedCategory ->
                        events.updateCategory(updatedCategory)
                    }
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    SelectSourceBottomSheetContent(
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceFrom?.id,
                        resetBottomSheetType = resetBottomSheetType
                    ) { updatedSource ->
                        events.updateSourceFrom(updatedSource)
                    }
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    SelectSourceBottomSheetContent(
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceTo?.id,
                        resetBottomSheetType = resetBottomSheetType
                    ) { updatedSource ->
                        events.updateSourceTo(updatedSource)
                    }
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            clearFocus()
        },
        backHandlerEnabled = addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = resetBottomSheetType,
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
                visible = data.uiVisibilityState.isTransactionTypesRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    items = transactionTypesForNewTransactionChipUIData,
                    selectedItemIndex = data.uiState.selectedTransactionTypeIndex,
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
                textFieldValue = data.uiState.amount,
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
                        data.uiState.amountErrorText.isNotNullOrBlank(),
                    ) {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_add_or_edit_transaction_amount_error_text,
                                data.uiState.amountErrorText.orEmpty(),
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.error,
                            ),
                        )
                    }
                },
                isError = data.uiState.amountErrorText.isNotNull(),
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
                visible = data.uiVisibilityState.isCategoryTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = data.uiState.category?.title.orEmpty(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_category,
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_CATEGORY
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
                visible = data.uiVisibilityState.isTitleTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = data.uiState.title,
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
                visible = data.uiVisibilityState.isTitleSuggestionsVisible && titleSuggestionsChipUIData.isNotEmpty(),
            ) {
                MyHorizontalScrollingSelectionGroup(
                    items = titleSuggestionsChipUIData,
                    onSelectionChange = { index ->
                        clearFocus()
                        events.updateTitle(TextFieldValue(data.titleSuggestions[index]))
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = data.uiVisibilityState.isTransactionForRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    items = transactionForValuesChipUIData,
                    selectedItemIndex = data.uiState.selectedTransactionForIndex,
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
                visible = data.uiVisibilityState.isDescriptionTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = data.uiState.description,
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
                visible = data.uiVisibilityState.isSourceFromTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = data.uiState.sourceFrom?.name.orEmpty(),
                    labelTextStringResourceId = if (data.selectedTransactionType == TransactionType.TRANSFER) {
                        R.string.screen_add_or_edit_transaction_source_from
                    } else {
                        R.string.screen_add_or_edit_transaction_source
                    },
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM
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
                visible = data.uiVisibilityState.isSourceToTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    value = data.uiState.sourceTo?.name.orEmpty(),
                    labelTextStringResourceId = if (data.selectedTransactionType == TransactionType.TRANSFER) {
                        R.string.screen_add_or_edit_transaction_source_to
                    } else {
                        R.string.screen_add_or_edit_transaction_source
                    },
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO
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
                value = data.uiState.transactionDate.formattedDate(),
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
                value = data.uiState.transactionTime.formattedTime(),
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
                textStringResourceId = data.ctaButtonLabelTextStringResourceId,
                isEnabled = data.isCtaButtonEnabled,
                onClick = {
                    clearFocus()
                    events.onCtaButtonClick()
                },
            )
        }
    }
}
