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
    public data object OnBottomSheetDismissed : AddOrEditTransactionScreenUIEvent()
    public data object OnNavigationBackButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnClearAmountButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnClearDescriptionButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnClearTitleButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditTransactionScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditTransactionScreenUIEvent()

    public data class OnAmountUpdated(
        val updatedAmount: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnDescriptionUpdated(
        val updatedDescription: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : AddOrEditTransactionScreenUIEvent()

    public data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : AddOrEditTransactionScreenUIEvent()
}
