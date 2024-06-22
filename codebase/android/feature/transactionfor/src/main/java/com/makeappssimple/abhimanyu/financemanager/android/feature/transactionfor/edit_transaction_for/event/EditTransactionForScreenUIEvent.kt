package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class EditTransactionForScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : EditTransactionForScreenUIEvent()
    data object OnClearTitleButtonClick : EditTransactionForScreenUIEvent()
    data object OnCtaButtonClick : EditTransactionForScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : EditTransactionForScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditTransactionForScreenUIEvent()
}
