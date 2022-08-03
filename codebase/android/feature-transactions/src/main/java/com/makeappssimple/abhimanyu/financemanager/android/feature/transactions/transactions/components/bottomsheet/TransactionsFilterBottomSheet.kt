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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

internal data class TransactionsFilterBottomSheetSelectionData(
    val selectedExpenseCategoryIndices: List<Int>,
    val selectedIncomeCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypeIndices: List<Int>,
)

internal data class TransactionsFilterBottomSheetData(
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val sources: List<Source>,
    val transactionTypes: List<TransactionType>,
    val selectedExpenseCategoryIndices: List<Int>,
    val selectedIncomeCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypesIndices: List<Int>,
    val onPositiveButtonClick: (data: TransactionsFilterBottomSheetSelectionData) -> Unit,
    val onNegativeButtonClick: () -> Unit,
)

@Composable
internal fun TransactionsFiltersBottomSheet(
    data: TransactionsFilterBottomSheetData,
) {
    val selectedExpenseCategoryIndices = remember {
        mutableStateListOf(
            *data.selectedExpenseCategoryIndices.toTypedArray(),
        )
    }
    val selectedIncomeCategoryIndices = remember {
        mutableStateListOf(
            *data.selectedIncomeCategoryIndices.toTypedArray(),
        )
    }
    val selectedSourceIndices = remember {
        mutableStateListOf(
            *data.selectedSourceIndices.toTypedArray(),
        )
    }
    val selectedTransactionTypesIndices = remember {
        mutableStateListOf(
            *data.selectedTransactionTypesIndices.toTypedArray(),
        )
    }

    Column(
        modifier = Modifier
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
                    style = TextStyle(
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    ),
                )
                TextButton(
                    onClick = {
                        selectedExpenseCategoryIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    )
                }
            }
            MySelectionGroup(
                items = data.expenseCategories
                    .map { category ->
                        ChipItem(
                            text = category.title,
                        )
                    },
                selectedItemsIndices = selectedExpenseCategoryIndices,
                onSelectionChange = { index ->
                    if (selectedExpenseCategoryIndices.contains(index)) {
                        selectedExpenseCategoryIndices.remove(index)
                    } else {
                        selectedExpenseCategoryIndices.add(index)
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
                    style = TextStyle(
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    ),
                )
                TextButton(
                    onClick = {
                        selectedIncomeCategoryIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    )
                }
            }
            MySelectionGroup(
                items = data.incomeCategories
                    .map { category ->
                        ChipItem(
                            text = category.title,
                        )
                    },
                selectedItemsIndices = selectedIncomeCategoryIndices,
                onSelectionChange = { index ->
                    if (selectedIncomeCategoryIndices.contains(index)) {
                        selectedIncomeCategoryIndices.remove(index)
                    } else {
                        selectedIncomeCategoryIndices.add(index)
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
                    style = TextStyle(
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    ),
                )
                TextButton(
                    onClick = {
                        selectedSourceIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    )
                }
            }
            MySelectionGroup(
                items = data.sources
                    .map { source ->
                        ChipItem(
                            text = source.name,
                        )
                    },
                selectedItemsIndices = selectedSourceIndices,
                onSelectionChange = { index ->
                    if (selectedSourceIndices.contains(index)) {
                        selectedSourceIndices.remove(index)
                    } else {
                        selectedSourceIndices.add(index)
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
                    style = TextStyle(
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    ),
                )
                TextButton(
                    onClick = {
                        selectedTransactionTypesIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    MyText(
                        textStringResourceId = R.string.bottom_sheet_transactions_filter_clear,
                    )
                }
            }
            MySelectionGroup(
                items = data.transactionTypes
                    .map { transactionType ->
                        ChipItem(
                            text = transactionType.title,
                        )
                    },
                selectedItemsIndices = selectedTransactionTypesIndices,
                onSelectionChange = { index ->
                    if (selectedTransactionTypesIndices.contains(index)) {
                        selectedTransactionTypesIndices.remove(index)
                    } else {
                        selectedTransactionTypesIndices.add(index)
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
                    selectedExpenseCategoryIndices.clear()
                    selectedIncomeCategoryIndices.clear()
                    selectedSourceIndices.clear()
                    selectedTransactionTypesIndices.clear()
                    data.onNegativeButtonClick()
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
                )
            }
            Button(
                onClick = {
                    data.onPositiveButtonClick(
                        TransactionsFilterBottomSheetSelectionData(
                            selectedExpenseCategoryIndices = selectedExpenseCategoryIndices,
                            selectedIncomeCategoryIndices = selectedIncomeCategoryIndices,
                            selectedSourceIndices = selectedSourceIndices,
                            selectedTransactionTypeIndices = selectedTransactionTypesIndices,
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
                )
            }
        }
    }
}
