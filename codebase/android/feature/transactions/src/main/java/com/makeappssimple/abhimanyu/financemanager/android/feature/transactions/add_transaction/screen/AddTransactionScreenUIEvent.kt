package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
public sealed class AddTransactionScreenUIEvent : ScreenUIEvent {
    public data object OnBottomSheetDismissed : AddTransactionScreenUIEvent()
    public data object OnNavigationBackButtonClick : AddTransactionScreenUIEvent()
    public data object OnClearAmountButtonClick : AddTransactionScreenUIEvent()
    public data object OnClearDescriptionButtonClick : AddTransactionScreenUIEvent()
    public data object OnClearTitleButtonClick : AddTransactionScreenUIEvent()
    public data object OnCtaButtonClick : AddTransactionScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddTransactionScreenUIEvent()
    public data object OnCategoryTextFieldClick : AddTransactionScreenUIEvent()
    public data object OnTransactionDateTextFieldClick : AddTransactionScreenUIEvent()
    public data object OnTransactionTimeTextFieldClick : AddTransactionScreenUIEvent()
    public data object OnAccountFromTextFieldClick : AddTransactionScreenUIEvent()
    public data object OnAccountToTextFieldClick : AddTransactionScreenUIEvent()
    public data object OnTransactionDatePickerDismissed : AddTransactionScreenUIEvent()
    public data object OnTransactionTimePickerDismissed : AddTransactionScreenUIEvent()

    public data class OnAmountUpdated(
        val updatedAmount: TextFieldValue,
    ) : AddTransactionScreenUIEvent()

    public data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : AddTransactionScreenUIEvent()

    public data class OnDescriptionUpdated(
        val updatedDescription: TextFieldValue,
    ) : AddTransactionScreenUIEvent()

    public data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddTransactionScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddTransactionScreenUIEvent()

    public data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : AddTransactionScreenUIEvent()

    public data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : AddTransactionScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddTransactionScreenUIEvent()

    public data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : AddTransactionScreenUIEvent()

    public data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : AddTransactionScreenUIEvent()
}
