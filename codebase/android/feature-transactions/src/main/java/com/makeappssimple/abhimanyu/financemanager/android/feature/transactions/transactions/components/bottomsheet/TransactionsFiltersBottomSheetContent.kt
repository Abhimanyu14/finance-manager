package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun TransactionsFiltersBottomSheetContent(
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
