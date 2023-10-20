package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface AccountsScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<AccountsScreenUIData>?>

    fun handleUIEvents(
        uiEvent: AccountsScreenUIEvent,
    )
}
