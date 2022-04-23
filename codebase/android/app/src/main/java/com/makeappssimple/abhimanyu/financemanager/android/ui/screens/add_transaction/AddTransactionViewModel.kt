package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface AddTransactionViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val transactionForValues: Array<TransactionFor>
    val transactionTypes: Array<TransactionType>
    val categories: Flow<List<Category>>
    val description: StateFlow<String>
    val title: StateFlow<String>
    val selectedTransactionTypeIndex: StateFlow<Int>
    val sources: Flow<List<Source>>
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val amount: StateFlow<String>
    val sourceFrom: StateFlow<Source?>
    val sourceTo: StateFlow<Source?>
    val category: StateFlow<Category?>
    val selectedTransactionForIndex: StateFlow<Int>
    val transactionCalendar: StateFlow<Calendar>

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun insertTransaction()

    fun isTitleTextFieldVisible(): Boolean

    fun isDescriptionTextFieldVisible(): Boolean

    fun isCategoryTextFieldVisible(): Boolean

    fun isTransactionForRadioGroupVisible(): Boolean

    fun isSourceFromTextFieldVisible(): Boolean

    fun isSourceToTextFieldVisible(): Boolean

    fun isValidTransactionData(): Boolean

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
        updatedSourceFrom: Source,
    )

    fun updateSourceTo(
        updatedSourceTo: Source,
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

    fun updateExpenseDefaultCategory(
        updatedExpenseDefaultCategory: Category?,
    )

    fun updateIncomeDefaultCategory(
        updatedIncomeDefaultCategory: Category?,
    )
}
