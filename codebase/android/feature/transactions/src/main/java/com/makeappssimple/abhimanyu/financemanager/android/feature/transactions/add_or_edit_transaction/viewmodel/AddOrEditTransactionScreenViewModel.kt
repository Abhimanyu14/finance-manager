package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditTransactionScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<AddOrEditTransactionScreenUIData>?>

    fun initViewModel()

    fun handleUIEvents(
        uiEvent: AddOrEditTransactionScreenUIEvent,
    )

    fun insertTransaction()

    fun updateTransaction()
}
