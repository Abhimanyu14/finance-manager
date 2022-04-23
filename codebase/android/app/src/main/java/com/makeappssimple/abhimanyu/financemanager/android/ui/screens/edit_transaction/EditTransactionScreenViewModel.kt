package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface EditTransactionScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager
    val transactionForValues: Array<TransactionFor>
    val transactionTypes: Array<TransactionType>
    val categories: StateFlow<List<Category>>
    val description: StateFlow<String>
    val title: StateFlow<String>
    val selectedTransactionTypeIndex: StateFlow<Int>
    val sources: StateFlow<List<Source>>
    val transactionTypesForNewTransaction: StateFlow<List<TransactionType>>
    val amount: StateFlow<String>
    val sourceFrom: StateFlow<Source?>
    val sourceTo: StateFlow<Source?>
    val category: StateFlow<Category?>
    val selectedTransactionForIndex: StateFlow<Int>
    val transactionCalendar: StateFlow<Calendar>
    val isValidTransactionData: StateFlow<Boolean>

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
}
