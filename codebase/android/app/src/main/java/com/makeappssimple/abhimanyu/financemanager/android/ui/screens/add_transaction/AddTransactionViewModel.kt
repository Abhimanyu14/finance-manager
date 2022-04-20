package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.ui.text.input.TextFieldValue
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
    val categoryTextFieldValue: TextFieldValue
    val sourceFromTextFieldValue: TextFieldValue
    val sourceToTextFieldValue: TextFieldValue
    val description: String
    val title: String
    val transactionDateTextFieldValue: TextFieldValue
    val transactionTimeTextFieldValue: TextFieldValue
    val selectedTransactionTypeIndex: Int
    val sources: Flow<List<Source>>
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val amount: String
    val sourceFrom: Source?
    val sourceTo: Source?
    val category: Category?
    val selectedTransactionForIndex: Int
    val expenseDefaultCategory: Category?
    val incomeDefaultCategory: Category?
    val transactionCalendar: Calendar

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
