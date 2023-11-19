package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
sealed class AddOrEditTransactionScreenUIEvent : ScreenUIEvent {
    data object ClearAmount : AddOrEditTransactionScreenUIEvent()
    data object ClearDescription : AddOrEditTransactionScreenUIEvent()
    data object ClearTitle : AddOrEditTransactionScreenUIEvent()
    data object NavigateUp : AddOrEditTransactionScreenUIEvent()
    data object OnCtaButtonClick : AddOrEditTransactionScreenUIEvent()

    data class UpdateAmount(
        val updatedAmount: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateCategory(
        val updatedCategory: Category?,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateDescription(
        val updatedDescription: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateSelectedTransactionForIndex(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateSelectedTransactionTypeIndex(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateAccountFrom(
        val updatedAccountFrom: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateAccountTo(
        val updatedAccountTo: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateTransactionDate(
        val updatedTransactionDate: LocalDate,
    ) : AddOrEditTransactionScreenUIEvent()

    data class UpdateTransactionTime(
        val updatedTransactionTime: LocalTime,
    ) : AddOrEditTransactionScreenUIEvent()
}
