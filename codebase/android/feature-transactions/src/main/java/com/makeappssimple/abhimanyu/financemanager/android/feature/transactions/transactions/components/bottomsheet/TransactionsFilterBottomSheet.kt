package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

@Immutable
internal data class TransactionsFilterBottomSheetSelectionData(
    val selectedExpenseCategoryIndices: List<Int>,
    val selectedIncomeCategoryIndices: List<Int>,
    val selectedInvestmentCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypeIndices: List<Int>,
)

@Immutable
internal data class TransactionFilterBottomSheetFilterGroupData(
    @StringRes val headingTextStringResourceId: Int,
    val items: List<ChipItem>,
    val selectedItemsIndices: SnapshotStateList<Int>,
    val onClearButtonClick: () -> Unit,
)

@Composable
internal fun TransactionsFiltersBottomSheet(
    modifier: Modifier = Modifier,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    sources: List<Source>,
    transactionTypes: List<TransactionType>,
    transactionsFilterBottomSheetSelectionData: TransactionsFilterBottomSheetSelectionData,
    onPositiveButtonClick: (data: TransactionsFilterBottomSheetSelectionData) -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    val expandedItemsIndices = remember {
        mutableStateListOf(
            transactionsFilterBottomSheetSelectionData.selectedExpenseCategoryIndices.isNotEmpty(),
            transactionsFilterBottomSheetSelectionData.selectedIncomeCategoryIndices.isNotEmpty(),
            transactionsFilterBottomSheetSelectionData.selectedInvestmentCategoryIndices.isNotEmpty(),
            transactionsFilterBottomSheetSelectionData.selectedSourceIndices.isNotEmpty(),
            transactionsFilterBottomSheetSelectionData.selectedTransactionTypeIndices.isNotEmpty(),
        )
    }

    val selectedExpenseCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = transactionsFilterBottomSheetSelectionData.selectedExpenseCategoryIndices.toTypedArray(),
        )
    }
    val selectedIncomeCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = transactionsFilterBottomSheetSelectionData.selectedIncomeCategoryIndices.toTypedArray(),
        )
    }
    val selectedInvestmentCategoryIndicesValue = remember {
        mutableStateListOf(
            elements = transactionsFilterBottomSheetSelectionData.selectedInvestmentCategoryIndices.toTypedArray(),
        )
    }
    val selectedSourceIndicesValue = remember {
        mutableStateListOf(
            elements = transactionsFilterBottomSheetSelectionData.selectedSourceIndices.toTypedArray(),
        )
    }
    val selectedTransactionTypeIndicesValue = remember {
        mutableStateListOf(
            elements = transactionsFilterBottomSheetSelectionData.selectedTransactionTypeIndices.toTypedArray(),
        )
    }

    val filters = listOf(
        TransactionFilterBottomSheetFilterGroupData(
            headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_expense_categories,
            items = expenseCategories.map { category ->
                ChipItem(
                    text = category.title,
                )
            },
            selectedItemsIndices = selectedExpenseCategoryIndicesValue,
            onClearButtonClick = {
                selectedExpenseCategoryIndicesValue.clear()
            },
        ),
        TransactionFilterBottomSheetFilterGroupData(
            headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_income_categories,
            items = incomeCategories.map { category ->
                ChipItem(
                    text = category.title,
                )
            },
            selectedItemsIndices = selectedIncomeCategoryIndicesValue,
            onClearButtonClick = {
                selectedIncomeCategoryIndicesValue.clear()
            },
        ),
        TransactionFilterBottomSheetFilterGroupData(
            headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_investment_categories,
            items = investmentCategories.map { category ->
                ChipItem(
                    text = category.title,
                )
            },
            selectedItemsIndices = selectedInvestmentCategoryIndicesValue,
            onClearButtonClick = {
                selectedInvestmentCategoryIndicesValue.clear()
            },
        ),
        TransactionFilterBottomSheetFilterGroupData(
            headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_sources,
            items = sources.map { source ->
                ChipItem(
                    text = source.name,
                )
            },
            selectedItemsIndices = selectedSourceIndicesValue,
            onClearButtonClick = {
                selectedSourceIndicesValue.clear()
            },
        ),
        TransactionFilterBottomSheetFilterGroupData(
            headingTextStringResourceId = R.string.bottom_sheet_transactions_filter_transaction_types,
            items = transactionTypes.map { transactionType ->
                ChipItem(
                    text = transactionType.title,
                )
            },
            selectedItemsIndices = selectedTransactionTypeIndicesValue,
            onClearButtonClick = {
                selectedTransactionTypeIndicesValue.clear()
            },
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
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
                    listItem.headingTextStringResourceId.hashCode()
                },
            ) { index, listItem ->
                TransactionFilterBottomSheetFilterGroup(
                    expanded = expandedItemsIndices[index],
                    headingTextStringResourceId = listItem.headingTextStringResourceId,
                    items = listItem.items,
                    selectedItemsIndices = listItem.selectedItemsIndices,
                    onClearButtonClick = listItem.onClearButtonClick,
                    onExpandButtonClick = {
                        expandedItemsIndices[index] = !expandedItemsIndices[index]
                    },
                    onItemClick = { clickedItemIndex ->
                        listItem.selectedItemsIndices.addIfDoesNotContainItemElseRemove(
                            item = clickedItemIndex,
                        )
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
                    selectedSourceIndicesValue.clear()
                    selectedTransactionTypeIndicesValue.clear()
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
                    onPositiveButtonClick(
                        TransactionsFilterBottomSheetSelectionData(
                            selectedExpenseCategoryIndices = selectedExpenseCategoryIndicesValue,
                            selectedIncomeCategoryIndices = selectedIncomeCategoryIndicesValue,
                            selectedInvestmentCategoryIndices = selectedInvestmentCategoryIndicesValue,
                            selectedSourceIndices = selectedSourceIndicesValue,
                            selectedTransactionTypeIndices = selectedTransactionTypeIndicesValue,
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
    }
}

@Composable
private fun TransactionFilterBottomSheetFilterGroup(
    expanded: Boolean,
    @StringRes headingTextStringResourceId: Int,
    items: List<ChipItem>,
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
                items = items,
                selectedItemsIndices = selectedItemsIndices,
                onSelectionChange = { index ->
                    onItemClick(index)
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
        }
    }
}
