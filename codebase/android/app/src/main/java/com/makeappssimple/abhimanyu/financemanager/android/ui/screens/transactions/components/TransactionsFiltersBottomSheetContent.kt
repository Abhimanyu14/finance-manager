package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun TransactionsFiltersBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    sources: List<Source>,
    transactionTypes: List<TransactionType>,
    selectedExpenseCategoryIndices: SnapshotStateList<Int>,
    selectedIncomeCategoryIndices: SnapshotStateList<Int>,
    selectedSourceIndices: SnapshotStateList<Int>,
    selectedTransactionTypesIndices: SnapshotStateList<Int>,
    resetBottomSheetType: () -> Unit,
) {
    TransactionsFiltersBottomSheet(
        data = TransactionsFilterBottomSheetData(
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            sources = sources,
            transactionTypes = transactionTypes,
            selectedExpenseCategoryIndices = selectedExpenseCategoryIndices,
            selectedIncomeCategoryIndices = selectedIncomeCategoryIndices,
            selectedSourceIndices = selectedSourceIndices,
            selectedTransactionTypesIndices = selectedTransactionTypesIndices,
            onPositiveButtonClick = {
                selectedExpenseCategoryIndices.clear()
                selectedIncomeCategoryIndices.clear()
                selectedSourceIndices.clear()
                selectedTransactionTypesIndices.clear()

                selectedExpenseCategoryIndices.addAll(it.selectedExpenseCategoryIndices)
                selectedIncomeCategoryIndices.addAll(it.selectedIncomeCategoryIndices)
                selectedSourceIndices.addAll(it.selectedSourceIndices)
                selectedTransactionTypesIndices.addAll(it.selectedTransactionTypeIndices)

                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                ) {
                    resetBottomSheetType()
                }
            },
            onNegativeButtonClick = {},
        ),
    )
}
