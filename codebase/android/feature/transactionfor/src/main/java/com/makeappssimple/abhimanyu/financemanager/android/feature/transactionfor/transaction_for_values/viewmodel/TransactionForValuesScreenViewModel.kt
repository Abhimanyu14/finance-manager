package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface TransactionForValuesScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<TransactionForValuesScreenUIData>?>

    public fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    )
}
