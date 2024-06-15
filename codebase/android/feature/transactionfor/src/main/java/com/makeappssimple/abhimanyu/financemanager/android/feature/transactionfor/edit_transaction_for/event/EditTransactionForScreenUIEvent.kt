package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class EditTransactionForScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : EditTransactionForScreenUIEvent()
    public data object OnClearTitleButtonClick : EditTransactionForScreenUIEvent()
    public data object OnCtaButtonClick : EditTransactionForScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : EditTransactionForScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditTransactionForScreenUIEvent()
}
