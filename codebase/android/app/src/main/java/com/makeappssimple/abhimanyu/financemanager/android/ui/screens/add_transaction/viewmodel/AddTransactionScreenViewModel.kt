package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface AddTransactionScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val transactionForValues: Array<TransactionFor>
    val categories: StateFlow<List<Category>>
    val sources: StateFlow<List<Source>>
    val uiState: StateFlow<AddTransactionScreenUiState>
    val uiVisibilityState: StateFlow<AddTransactionScreenUiVisibilityState>
    val selectedTransactionType: StateFlow<TransactionType?>
    val isValidTransactionData: StateFlow<Boolean>
    val titleSuggestions: StateFlow<List<String>>

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun insertTransaction()

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
