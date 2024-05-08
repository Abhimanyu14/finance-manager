package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AddOrEditTransactionScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AddOrEditTransactionScreenUIData>?>

    public fun initViewModel()

    public fun handleUIEvent(
        uiEvent: AddOrEditTransactionScreenUIEvent,
    )

    public fun insertTransaction()

    public fun updateTransaction()
}
