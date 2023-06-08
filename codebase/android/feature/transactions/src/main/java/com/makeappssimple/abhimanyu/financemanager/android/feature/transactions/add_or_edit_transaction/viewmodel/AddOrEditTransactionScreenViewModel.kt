package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime

interface AddOrEditTransactionScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<AddOrEditTransactionScreenUIData?>

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

    fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    )

    fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    )
}
