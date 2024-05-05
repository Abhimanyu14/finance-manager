package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
public sealed class AddOrEditTransactionScreenUIEvent : ScreenUIEvent {
    public data object ClearAmount : AddOrEditTransactionScreenUIEvent()
    public data object ClearDescription : AddOrEditTransactionScreenUIEvent()
    public data object ClearTitle : AddOrEditTransactionScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditTransactionScreenUIEvent()

    public data class UpdateAmount(
        val updatedAmount: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateCategory(
        val updatedCategory: Category?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateDescription(
        val updatedDescription: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateSelectedTransactionForIndex(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateSelectedTransactionTypeIndex(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateAccountFrom(
        val updatedAccountFrom: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateAccountTo(
        val updatedAccountTo: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateTransactionDate(
        val updatedTransactionDate: LocalDate,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class UpdateTransactionTime(
        val updatedTransactionTime: LocalTime,
    ) : AddOrEditTransactionScreenUIEvent()
}
