package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddTransactionForScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : AddTransactionForScreenUIEvent()
    public data object OnClearTitleButtonClick : AddTransactionForScreenUIEvent()
    public data object OnCtaButtonClick : AddTransactionForScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddTransactionForScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddTransactionForScreenUIEvent()
}
