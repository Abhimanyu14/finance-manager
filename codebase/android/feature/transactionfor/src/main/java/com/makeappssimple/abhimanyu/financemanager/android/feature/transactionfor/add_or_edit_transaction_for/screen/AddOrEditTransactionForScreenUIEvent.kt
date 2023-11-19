package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class AddOrEditTransactionForScreenUIEvent : ScreenUIEvent {
    data object ClearTitle : AddOrEditTransactionForScreenUIEvent()
    data object NavigateUp : AddOrEditTransactionForScreenUIEvent()
    data object OnCtaButtonClick : AddOrEditTransactionForScreenUIEvent()
    data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditTransactionForScreenUIEvent()
}
