package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface EditTransactionScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val transactionForValues: Array<TransactionFor>
    val categories: StateFlow<List<Category>>
    val sources: StateFlow<List<Source>>
    val uiState: StateFlow<EditTransactionScreenUiState>
    val uiVisibilityState: StateFlow<EditTransactionScreenUiVisibilityState>
    val selectedTransactionType: StateFlow<TransactionType?>
    val isValidTransactionData: StateFlow<Boolean>
    val titleSuggestions: StateFlow<List<String>>

    fun updateTransaction()

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateTitle(
        updatedTitle: String,
    )

    fun clearTitle()

    fun updateDescription(
        updatedDescription: String,
    )

    fun clearDescription()

    fun updateAmount(
        updatedAmount: String,
    )

    fun clearAmount()

    fun updateSourceFrom(
        updatedSourceFrom: Source?,
    )

    fun updateSourceTo(
        updatedSourceTo: Source?,
    )

    fun updateCategory(
        updatedCategory: Category?,
    )

    fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    )

    fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    )
}
