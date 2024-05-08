package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface ViewTransactionScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<ViewTransactionScreenUIData>?>

    public fun initViewModel()

    public fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    )
}
