package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.statusBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight
import java.time.LocalDate

@Composable
public fun TransactionsFiltersBottomSheetUI(
    modifier: Modifier = Modifier,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    accounts: List<Account>,
    transactionForValues: List<TransactionFor>,
    transactionTypes: List<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    selectedFilter: Filter,
    onPositiveButtonClick: (filter: Filter) -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    val expandedItemsIndices = remember {
        mutableStateListOf(
            selectedFilter.selectedExpenseCategoryIndices.isNotEmpty(),
            selectedFilter.selectedIncomeCategoryIndices.isNotEmpty(),
            selectedFilter.selectedInvestmentCategoryIndices.isNotEmpty(),
            selectedFilter.selectedAccountsIndices.isNotEmpty(),
            selectedFilter.selectedTransactionForValuesIndices.isNotEmpty(),
            selectedFilter.selectedTransactionTypeIndices.isNotEmpty(),
            selectedFilter.toDate.isNotNull(),
        )
    }

    val selectedExpenseCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedExpenseCategoryIndices.toTypedArray(),
        )
    }
    val selectedIncomeCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedIncomeCategoryIndices.toTypedArray(),
        )
    }
    val selectedInvestmentCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedInvestmentCategoryIndices.toTypedArray(),
        )
    }
    val selectedAccountIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedAccountsIndices.toTypedArray(),
        )
    }
    val selectedTransactionForValuesIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedTransactionForValuesIndices.toTypedArray(),
        )
    }
    val selectedTransactionTypeIndicesValue = remember {
        mutableStateListOf(
            elements = selectedFilter.selectedTransactionTypeIndices.toTypedArray(),
        )
    }
    var fromDate by remember {
        mutableStateOf(
            value = selectedFilter.fromDate ?: defaultMinDate,
        )
    }
    var toDate by remember {
        mutableStateOf(
            value = selectedFilter.toDate ?: defaultMaxDate,
        )
    }
    val filters = buildList {
        if (expenseCategories.isNotEmpty() && expenseCategories.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_expense_categories,
                        items = expenseCategories.map { category ->
                            ChipUIData(
                                text = category.title,
                            )
                        },
                        selectedItemsIndices = selectedExpenseCategoryIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedExpenseCategoryIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
        if (incomeCategories.isNotEmpty() && incomeCategories.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_income_categories,
                        items = incomeCategories.map { category ->
                            ChipUIData(
                                text = category.title,
                            )
                        },
                        selectedItemsIndices = selectedIncomeCategoryIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedIncomeCategoryIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
        if (investmentCategories.isNotEmpty() && investmentCategories.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_investment_categories,
                        items = investmentCategories.map { category ->
                            ChipUIData(
                                text = category.title,
                            )
                        },
                        selectedItemsIndices = selectedInvestmentCategoryIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedInvestmentCategoryIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
        if (accounts.isNotEmpty() && accounts.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_accounts,
                        items = accounts.map { account ->
                            ChipUIData(
                                text = account.name,
                            )
                        },
                        selectedItemsIndices = selectedAccountIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedAccountIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
        if (transactionForValues.isNotEmpty() && transactionForValues.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_transaction_for_values,
                        items = transactionForValues.map { transactionFor ->
                            ChipUIData(
                                text = transactionFor.titleToDisplay,
                            )
                        },
                        selectedItemsIndices = selectedTransactionForValuesIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedTransactionForValuesIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
        if (transactionTypes.isNotEmpty() && transactionTypes.size > 1) {
            add(
                TransactionsFiltersBottomSheetData(
                    data = TransactionFilterBottomSheetFilterGroupData(
                        headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_transaction_types,
                        items = transactionTypes.map { transactionType ->
                            ChipUIData(
                                text = transactionType.title,
                            )
                        },
                        selectedItemsIndices = selectedTransactionTypeIndicesValue,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick -> {
                                selectedTransactionTypeIndicesValue.clear()
                            }
                        }
                    },
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarSpacer()
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1F,
                ),
            contentPadding = PaddingValues(
                top = 16.dp,
            ),
        ) {
            itemsIndexed(
                items = filters,
                key = { _, listItem ->
                    listItem.data.headingTextStringResourceId.hashCode()
                },
            ) { index, listItem ->
                TransactionFilterBottomSheetFilterGroup(
                    isExpanded = expandedItemsIndices[index],
                    headingTextStringResourceId = listItem.data.headingTextStringResourceId,
                    items = listItem.data.items,
                    selectedItemsIndices = listItem.data.selectedItemsIndices,
                    onClearButtonClick = {
                        listItem.handleEvent(TransactionFilterBottomSheetFilterGroupEvent.OnClearButtonClick)
                    },
                    onExpandButtonClick = {
                        expandedItemsIndices[index] = !expandedItemsIndices[index]
                    },
                    onItemClick = { clickedItemIndex ->
                        listItem.data.selectedItemsIndices.addIfDoesNotContainItemElseRemove(
                            item = clickedItemIndex,
                        )
                    },
                )
            }
            item {
                TransactionFilterBottomSheetDateFilter(
                    isExpanded = expandedItemsIndices[filters.lastIndex + 1],
                    headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_transaction_date,
                    onClearButtonClick = {
                        fromDate = defaultMinDate
                        toDate = defaultMaxDate
                    },
                    onExpandButtonClick = {
                        expandedItemsIndices[filters.lastIndex + 1] =
                            !expandedItemsIndices[filters.lastIndex + 1]
                    },
                    minDate = defaultMinDate,
                    maxDate = defaultMaxDate,
                    fromDate = fromDate,
                    toDate = toDate,
                    updateFromDate = {
                        fromDate = it
                    },
                ) {
                    toDate = it
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    selectedExpenseCategoryIndicesValue.clear()
                    selectedIncomeCategoryIndicesValue.clear()
                    selectedInvestmentCategoryIndicesValue.clear()
                    selectedAccountIndicesValue.clear()
                    selectedTransactionForValuesIndicesValue.clear()
                    selectedTransactionTypeIndicesValue.clear()
                    fromDate = defaultMinDate
                    toDate = defaultMaxDate
                    onNegativeButtonClick()
                },
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_reset,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    val isFromDateSameAsOldestTransactionDate = fromDate == defaultMinDate
                    val isToDateSameAsCurrentDayDate = toDate == defaultMaxDate
                    val isDateFilterCleared = isFromDateSameAsOldestTransactionDate &&
                            isToDateSameAsCurrentDayDate
                    onPositiveButtonClick(
                        Filter(
                            selectedExpenseCategoryIndices = selectedExpenseCategoryIndicesValue,
                            selectedIncomeCategoryIndices = selectedIncomeCategoryIndicesValue,
                            selectedInvestmentCategoryIndices = selectedInvestmentCategoryIndicesValue,
                            selectedAccountsIndices = selectedAccountIndicesValue,
                            selectedTransactionForValuesIndices = selectedTransactionForValuesIndicesValue,
                            selectedTransactionTypeIndices = selectedTransactionTypeIndicesValue,
                            fromDate = fromDate,
                            toDate = if (isDateFilterCleared) {
                                null
                            } else {
                                toDate
                            },
                        )
                    )
                },
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_apply,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarsAndImeSpacer()
    }
}

@Composable
private fun TransactionFilterBottomSheetFilterGroup(
    isExpanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    items: List<ChipUIData>,
    selectedItemsIndices: List<Int>,
    onClearButtonClick: () -> Unit,
    onExpandButtonClick: () -> Unit,
    onItemClick: (index: Int) -> Unit,
) {
    val chevronDegrees: Float by animateFloatAsState(
        targetValue = if (isExpanded) {
            90F
        } else {
            0F
        },
        label = "chevron_degrees",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        onExpandButtonClick()
                    }
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyIconButton(
                    imageVector = MyIcons.ChevronRight,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescriptionStringResourceId = if (isExpanded) {
                        R.string.bottom_sheet_transactions_filter_collapse_group
                    } else {
                        R.string.bottom_sheet_transactions_filter_expand_group
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = chevronDegrees
                        },
                    onClick = onExpandButtonClick,
                )
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    textStringResourceId = headingTextStringResourceId,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
            }
            TextButton(
                onClick = {
                    onClearButtonClick()
                },
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        if (isExpanded) {
            MySelectionGroup(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MySelectionGroupData(
                    items = items,
                    selectedItemsIndices = selectedItemsIndices,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MySelectionGroupEvent.OnSelectionChange -> {
                            onItemClick(event.index)
                        }
                    }
                },
            )
        }
    }
}

@Composable
public fun TransactionFilterBottomSheetDateFilter(
    isExpanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    onClearButtonClick: () -> Unit,
    onExpandButtonClick: () -> Unit,
    minDate: LocalDate,
    maxDate: LocalDate,
    fromDate: LocalDate,
    toDate: LocalDate,
    updateFromDate: (updatedFromDate: LocalDate) -> Unit,
    updateToDate: (updatedToDate: LocalDate) -> Unit,
) {
    val chevronDegrees: Float by animateFloatAsState(
        targetValue = if (isExpanded) {
            90F
        } else {
            0F
        },
        label = "chevron_degrees",
    )
    var isFromDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }
    var isToDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }

    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isFromDatePickerDialogVisible,
            endLocalDate = toDate,
            selectedLocalDate = fromDate,
            startLocalDate = minDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    isFromDatePickerDialogVisible = false
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
                    updateFromDate(event.selectedDate)
                    isFromDatePickerDialogVisible = false
                }
            }
        },
    )
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isToDatePickerDialogVisible,
            endLocalDate = maxDate,
            selectedLocalDate = toDate,
            startLocalDate = fromDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    isToDatePickerDialogVisible = false
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
                    updateToDate(event.selectedDate)
                    isToDatePickerDialogVisible = false
                }
            }
        },
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        onExpandButtonClick()
                    }
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyIconButton(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = chevronDegrees
                        },
                    tint = MaterialTheme.colorScheme.onBackground,
                    imageVector = MyIcons.ChevronRight,
                    contentDescriptionStringResourceId = if (isExpanded) {
                        R.string.bottom_sheet_transactions_filter_collapse_group
                    } else {
                        R.string.bottom_sheet_transactions_filter_expand_group
                    },
                    onClick = {
                        onExpandButtonClick()
                    },
                )
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    textStringResourceId = headingTextStringResourceId,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
            }
            TextButton(
                onClick = {
                    onClearButtonClick()
                },
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        if (isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            horizontal = 8.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        value = fromDate.formattedDate(),
                        labelTextStringResourceId = R.string.bottom_sheet_transactions_filter_from_date,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                isFromDatePickerDialogVisible = true
                            }
                        }
                    },
                )
                MyReadOnlyTextField(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            horizontal = 8.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        value = toDate.formattedDate(),
                        labelTextStringResourceId = R.string.bottom_sheet_transactions_filter_to_date,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                isToDatePickerDialogVisible = true
                            }
                        }
                    },
                )
            }
        }
    }
}
