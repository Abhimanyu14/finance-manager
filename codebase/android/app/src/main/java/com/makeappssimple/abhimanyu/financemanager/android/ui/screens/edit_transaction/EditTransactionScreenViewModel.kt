package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

data class EditTransactionScreenUiState(
    val selectedTransactionTypeIndex: Int?,
    val amount: String,
    val title: String,
    val description: String,
    val category: Category?,
    val selectedTransactionForIndex: Int,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val transactionCalendar: Calendar,
)

data class EditTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isDescriptionTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isSourceFromTextFieldVisible: Boolean = false,
    val isSourceToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
)

interface EditTransactionScreenViewModel : BaseScreenViewModel {
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
