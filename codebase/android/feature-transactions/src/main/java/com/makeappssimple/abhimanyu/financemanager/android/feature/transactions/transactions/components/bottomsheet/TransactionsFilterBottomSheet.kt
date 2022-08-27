package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

internal data class TransactionsFilterBottomSheetSelectionData(
    val selectedExpenseCategoryIndices: List<Int>,
    val selectedIncomeCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypeIndices: List<Int>,
)

@Composable
internal fun TransactionsFiltersBottomSheet(
    modifier: Modifier = Modifier,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    sources: List<Source>,
    transactionTypes: List<TransactionType>,
    selectedExpenseCategoryIndices: List<Int>,
    selectedIncomeCategoryIndices: List<Int>,
    selectedSourceIndices: List<Int>,
    selectedTransactionTypesIndices: List<Int>,
    onPositiveButtonClick: (data: TransactionsFilterBottomSheetSelectionData) -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    val selectedExpenseCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = selectedExpenseCategoryIndices.toTypedArray(),
        )
    }
    val selectedIncomeCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = selectedIncomeCategoryIndices.toTypedArray(),
        )
    }
    val selectedSourceIndicesValue = remember {
        mutableStateListOf(
            elements = selectedSourceIndices.toTypedArray(),
        )
    }
    val selectedTransactionTypesIndicesValue = remember {
        mutableStateListOf(
            elements = selectedTransactionTypesIndices.toTypedArray(),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1F,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            VerticalSpacer(
                height = 16.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_select_expense_categories,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedExpenseCategoryIndicesValue.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            MySelectionGroup(
                items = expenseCategories
                    .map { category ->
                        ChipItem(
                            text = category.title,
                        )
                    },
                selectedItemsIndices = selectedExpenseCategoryIndicesValue,
                onSelectionChange = { index ->
                    if (selectedExpenseCategoryIndicesValue.contains(index)) {
                        selectedExpenseCategoryIndicesValue.remove(index)
                    } else {
                        selectedExpenseCategoryIndicesValue.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_select_income_categories,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedIncomeCategoryIndicesValue.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            MySelectionGroup(
                items = incomeCategories
                    .map { category ->
                        ChipItem(
                            text = category.title,
                        )
                    },
                selectedItemsIndices = selectedIncomeCategoryIndicesValue,
                onSelectionChange = { index ->
                    if (selectedIncomeCategoryIndicesValue.contains(index)) {
                        selectedIncomeCategoryIndicesValue.remove(index)
                    } else {
                        selectedIncomeCategoryIndicesValue.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_select_sources,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedSourceIndicesValue.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            MySelectionGroup(
                items = sources
                    .map { source ->
                        ChipItem(
                            text = source.name,
                        )
                    },
                selectedItemsIndices = selectedSourceIndicesValue,
                onSelectionChange = { index ->
                    if (selectedSourceIndicesValue.contains(index)) {
                        selectedSourceIndicesValue.remove(index)
                    } else {
                        selectedSourceIndicesValue.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_select_transaction_types,
                    style = MaterialTheme.typography.headlineLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedTransactionTypesIndicesValue.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            MySelectionGroup(
                items = transactionTypes
                    .map { transactionType ->
                        ChipItem(
                            text = transactionType.title,
                        )
                    },
                selectedItemsIndices = selectedTransactionTypesIndicesValue,
                onSelectionChange = { index ->
                    if (selectedTransactionTypesIndicesValue.contains(index)) {
                        selectedTransactionTypesIndicesValue.remove(index)
                    } else {
                        selectedTransactionTypesIndicesValue.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    selectedExpenseCategoryIndicesValue.clear()
                    selectedIncomeCategoryIndicesValue.clear()
                    selectedSourceIndicesValue.clear()
                    selectedTransactionTypesIndicesValue.clear()
                    onNegativeButtonClick()
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_reset,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(
                onClick = {
                    onPositiveButtonClick(
                        TransactionsFilterBottomSheetSelectionData(
                            selectedExpenseCategoryIndices = selectedExpenseCategoryIndicesValue,
                            selectedIncomeCategoryIndices = selectedIncomeCategoryIndicesValue,
                            selectedSourceIndices = selectedSourceIndicesValue,
                            selectedTransactionTypeIndices = selectedTransactionTypesIndicesValue,
                        )
                    )
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_transactions_filter_apply,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
