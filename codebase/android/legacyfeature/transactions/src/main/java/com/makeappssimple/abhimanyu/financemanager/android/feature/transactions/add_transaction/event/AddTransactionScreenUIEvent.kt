package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
internal sealed class AddTransactionScreenUIEvent : ScreenUIEvent {
    data object OnBottomSheetDismissed : AddTransactionScreenUIEvent()
    data object OnNavigationBackButtonClick : AddTransactionScreenUIEvent()
    data object OnClearAmountButtonClick : AddTransactionScreenUIEvent()
    data object OnClearTitleButtonClick : AddTransactionScreenUIEvent()
    data object OnCtaButtonClick : AddTransactionScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AddTransactionScreenUIEvent()
    data object OnCategoryTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionDateTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionTimeTextFieldClick : AddTransactionScreenUIEvent()
    data object OnAccountFromTextFieldClick : AddTransactionScreenUIEvent()
    data object OnAccountToTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionDatePickerDismissed : AddTransactionScreenUIEvent()
    data object OnTransactionTimePickerDismissed : AddTransactionScreenUIEvent()
    data object OnSnackbarDismissed : AddTransactionScreenUIEvent()

    data class OnAmountUpdated(
        val updatedAmount: TextFieldValue,
    ) : AddTransactionScreenUIEvent()

    data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : AddTransactionScreenUIEvent()

    data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddTransactionScreenUIEvent()

    data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddTransactionScreenUIEvent()

    data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : AddTransactionScreenUIEvent()

    data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : AddTransactionScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddTransactionScreenUIEvent()

    data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : AddTransactionScreenUIEvent()

    data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : AddTransactionScreenUIEvent()
}
