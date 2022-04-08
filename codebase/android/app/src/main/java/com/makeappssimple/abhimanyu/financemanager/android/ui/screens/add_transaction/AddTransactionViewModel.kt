package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface AddTransactionViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val transactionForValues: Array<TransactionFor>
    val transactionTypes: Array<TransactionType>
    val categories: Flow<List<Category>>
    var expenseDefaultSource: Source?
    var incomeDefaultSource: Source?
    var expenseDefaultCategory: Category?
    var incomeDefaultCategory: Category?
    var amount: String
    var category: Category?
    val categoryTextFieldValue: TextFieldValue
    var sourceFrom: Source?
    val sourceFromTextFieldValue: TextFieldValue
    var sourceTo: Source?
    val sourceToTextFieldValue: TextFieldValue
    var description: String
    var title: String
    var transactionCalendar: Calendar
    val transactionDateTextFieldValue: TextFieldValue
    val transactionTimeTextFieldValue: TextFieldValue
    var selectedTransactionForIndex: Int
    var selectedTransactionTypeIndex: Int
    val sources: Flow<List<Source>>


    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun insertTransaction()

    fun getTransactionTypesForNewTransaction(): Array<TransactionType>

    fun isTitleTextFieldVisible(): Boolean

    fun isDescriptionTextFieldVisible(): Boolean

    fun isCategoryTextFieldVisible(): Boolean

    fun isTransactionForRadioGroupVisible(): Boolean

    fun isSourceFromTextFieldVisible(): Boolean

    fun isSourceToTextFieldVisible(): Boolean

    fun isValidTransactionData(): Boolean
}
