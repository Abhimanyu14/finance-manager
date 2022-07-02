package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
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
    selectedExpenseCategoryIndices: List<Int>,
    selectedIncomeCategoryIndices: List<Int>,
    selectedSourceIndices: List<Int>,
    selectedTransactionTypesIndices: List<Int>,
    updateSelectedExpenseCategoryIndices: (updatedSelectedExpenseCategoryIndices: List<Int>) -> Unit,
    updateSelectedIncomeCategoryIndices: (updatedSelectedIncomeCategoryIndices: List<Int>) -> Unit,
    updateSelectedSourceIndices: (updatedSelectedSourceIndices: List<Int>) -> Unit,
    updateSelectedTransactionTypesIndices: (updatedSelectedTransactionTypesIndices: List<Int>) -> Unit,
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
                updateSelectedExpenseCategoryIndices(it.selectedExpenseCategoryIndices)
                updateSelectedIncomeCategoryIndices(it.selectedIncomeCategoryIndices)
                updateSelectedSourceIndices(it.selectedSourceIndices)
                updateSelectedTransactionTypesIndices(it.selectedTransactionTypeIndices)
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
