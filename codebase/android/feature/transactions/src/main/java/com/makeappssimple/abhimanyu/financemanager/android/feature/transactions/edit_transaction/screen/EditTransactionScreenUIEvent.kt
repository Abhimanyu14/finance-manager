package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
public sealed class EditTransactionScreenUIEvent : ScreenUIEvent {
    public data object OnBottomSheetDismissed : EditTransactionScreenUIEvent()
    public data object OnNavigationBackButtonClick : EditTransactionScreenUIEvent()
    public data object OnClearAmountButtonClick : EditTransactionScreenUIEvent()
    public data object OnClearDescriptionButtonClick : EditTransactionScreenUIEvent()
    public data object OnClearTitleButtonClick : EditTransactionScreenUIEvent()
    public data object OnCtaButtonClick : EditTransactionScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : EditTransactionScreenUIEvent()
    public data object OnCategoryTextFieldClick : EditTransactionScreenUIEvent()
    public data object OnTransactionDateTextFieldClick : EditTransactionScreenUIEvent()
    public data object OnTransactionTimeTextFieldClick : EditTransactionScreenUIEvent()
    public data object OnAccountFromTextFieldClick : EditTransactionScreenUIEvent()
    public data object OnAccountToTextFieldClick : EditTransactionScreenUIEvent()
    public data object OnTransactionDatePickerDismissed : EditTransactionScreenUIEvent()
    public data object OnTransactionTimePickerDismissed : EditTransactionScreenUIEvent()

    public data class OnAmountUpdated(
        val updatedAmount: TextFieldValue,
    ) : EditTransactionScreenUIEvent()

    public data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : EditTransactionScreenUIEvent()

    public data class OnDescriptionUpdated(
        val updatedDescription: TextFieldValue,
    ) : EditTransactionScreenUIEvent()

    public data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : EditTransactionScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : EditTransactionScreenUIEvent()

    public data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : EditTransactionScreenUIEvent()

    public data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : EditTransactionScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditTransactionScreenUIEvent()

    public data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : EditTransactionScreenUIEvent()

    public data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : EditTransactionScreenUIEvent()
}
