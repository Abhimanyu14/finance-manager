package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AddOrEditTransactionForScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AddOrEditTransactionForScreenUIData>?>

    public fun initViewModel()

    public fun handleUIEvent(
        uiEvent: AddOrEditTransactionForScreenUIEvent,
    )

    public fun insertTransactionFor()

    public fun updateTransactionFor()
}
