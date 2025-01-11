package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class AddTransactionForScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : AddTransactionForScreenUIEvent()
    data object OnClearTitleButtonClick : AddTransactionForScreenUIEvent()
    data object OnCtaButtonClick : AddTransactionForScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AddTransactionForScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddTransactionForScreenUIEvent()
}
