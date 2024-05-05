package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddOrEditTransactionForScreenUIEvent : ScreenUIEvent {
    public data object ClearTitle : AddOrEditTransactionForScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditTransactionForScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditTransactionForScreenUIEvent()
    public data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditTransactionForScreenUIEvent()
}
