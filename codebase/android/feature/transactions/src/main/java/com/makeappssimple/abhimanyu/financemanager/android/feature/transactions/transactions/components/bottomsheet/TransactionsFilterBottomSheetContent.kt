package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet

import android.content.Context
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

@Composable
internal fun TransactionsFilterBottomSheetContent(
    context: Context,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    sources: List<Source>,
    transactionTypes: List<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    currentTimeMillis: Long,
    selectedFilter: Filter,
    updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    TransactionsFiltersBottomSheet(
        context = context,
        expenseCategories = expenseCategories,
        incomeCategories = incomeCategories,
        investmentCategories = investmentCategories,
        sources = sources,
        transactionTypes = transactionTypes,
        defaultMinDate = defaultMinDate,
        defaultMaxDate = defaultMaxDate,
        currentTimeMillis = currentTimeMillis,
        selectedFilter = selectedFilter,
        onPositiveButtonClick = {
            updateSelectedFilter(it)
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                resetBottomSheetType()
            }
        },
        onNegativeButtonClick = {},
    )
}
