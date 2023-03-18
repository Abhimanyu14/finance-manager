package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface AddOrEditTransactionScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val uiState: StateFlow<AddOrEditTransactionScreenUiState>
    val uiVisibilityState: StateFlow<AddOrEditTransactionScreenUiVisibilityState>
    val isCtaButtonEnabled: StateFlow<Boolean>
    val filteredCategories: StateFlow<List<Category>>
    val sources: StateFlow<List<Source>>
    val titleSuggestions: StateFlow<List<String>>
    val transactionForValues: StateFlow<List<TransactionFor>>
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val selectedTransactionType: StateFlow<TransactionType?>

    fun insertTransaction()

    fun updateTransaction()

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun clearTitle()

    fun updateDescription(
        updatedDescription: TextFieldValue,
    )

    fun clearDescription()

    fun updateAmount(
        updatedAmount: TextFieldValue,
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
