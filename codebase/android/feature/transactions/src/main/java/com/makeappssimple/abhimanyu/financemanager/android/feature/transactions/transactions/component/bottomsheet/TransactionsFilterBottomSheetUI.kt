package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet

import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.statusBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import java.time.LocalDate

@Immutable
internal data class TransactionsFiltersBottomSheetData(
    val data: TransactionFilterBottomSheetFilterGroupData,
    val events: TransactionFilterBottomSheetFilterGroupEvents,
)

@Immutable
internal data class TransactionFilterBottomSheetFilterGroupData(
    @StringRes val headingTextStringResourceId: Int,
    val items: List<ChipUIData>,
    val selectedItemsIndices: SnapshotStateList<Int>,
)

@Immutable
internal data class TransactionFilterBottomSheetFilterGroupEvents(
    val onClearButtonClick: () -> Unit,
)

@Composable
internal fun TransactionsFiltersBottomSheetUI(
    modifier: Modifier = Modifier,
    context: Context,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    accounts: List<Account>,
    transactionForValues: List<TransactionFor>,
    transactionTypes: List<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    currentTimeMillis: Long,
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedExpenseCategoryIndicesValue.clear()
                        },
                    ),
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedIncomeCategoryIndicesValue.clear()
                        },
                    ),
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedInvestmentCategoryIndicesValue.clear()
                        },
                    ),
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedAccountIndicesValue.clear()
                        },
                    ),
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedTransactionForValuesIndicesValue.clear()
                        },
                    ),
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
                    events = TransactionFilterBottomSheetFilterGroupEvents(
                        onClearButtonClick = {
                            selectedTransactionTypeIndicesValue.clear()
                        },
                    ),
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarSpacer()
            .defaultMinSize(
                minHeight = 24.dp,
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
                    expanded = expandedItemsIndices[index],
                    headingTextStringResourceId = listItem.data.headingTextStringResourceId,
                    items = listItem.data.items,
                    selectedItemsIndices = listItem.data.selectedItemsIndices,
                    onClearButtonClick = listItem.events.onClearButtonClick,
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
                    expanded = expandedItemsIndices[filters.lastIndex + 1],
                    context = context,
                    headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_transaction_date,
                    currentTimeMillis = currentTimeMillis,
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
                    updateToDate = {
                        toDate = it
                    },
                )
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
        NavigationBarSpacer()
    }
}

@Composable
private fun TransactionFilterBottomSheetFilterGroup(
    expanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    items: List<ChipUIData>,
    selectedItemsIndices: List<Int>,
    onClearButtonClick: () -> Unit,
    onExpandButtonClick: () -> Unit,
    onItemClick: (index: Int) -> Unit,
) {
    val chevronDegrees: Float by animateFloatAsState(
        targetValue = if (expanded) {
            90F
        } else {
            0F
        },
        label = "", // TODO(Abhi): Add label for animation inspection
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
                IconButton(
                    onClick = {
                        onExpandButtonClick()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(
                                degrees = chevronDegrees,
                            ),
                    )
                }
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
        if (expanded) {
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
                events = MySelectionGroupEvents(
                    onSelectionChange = { index ->
                        onItemClick(index)
                    },
                ),
            )
        }
    }
}

@Composable
fun TransactionFilterBottomSheetDateFilter(
    expanded: Boolean,
    context: Context,
    @StringRes headingTextStringResourceId: Int,
    currentTimeMillis: Long,
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
        targetValue = if (expanded) {
            90F
        } else {
            0F
        },
        label = "", // TODO(Abhi): Add label for animation inspection
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
        events = MyDatePickerEvents(
            onPositiveButtonClick = {
                updateFromDate(it)
                isFromDatePickerDialogVisible = false
            },
            onNegativeButtonClick = {
                isFromDatePickerDialogVisible = false
            },
        )
    )
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isToDatePickerDialogVisible,
            endLocalDate = maxDate,
            selectedLocalDate = toDate,
            startLocalDate = fromDate,
        ),
        events = MyDatePickerEvents(
            onPositiveButtonClick = {
                updateToDate(it)
                isToDatePickerDialogVisible = false
            },
            onNegativeButtonClick = {
                isToDatePickerDialogVisible = false
            },
        )
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
                IconButton(
                    onClick = {
                        onExpandButtonClick()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(
                                degrees = chevronDegrees,
                            ),
                    )
                }
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
        if (expanded) {
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
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            isFromDatePickerDialogVisible = true
                        },
                    ),
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
                    events = MyReadOnlyTextFieldEvents(
                        onClick = {
                            isToDatePickerDialogVisible = true
                        },
                    ),
                )
            }
        }
    }
}
