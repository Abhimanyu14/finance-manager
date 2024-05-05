package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AccountsScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AccountsScreenUIData>?>

    public fun handleUIEvents(
        uiEvent: AccountsScreenUIEvent,
    )

    public fun deleteAccount(
        accountId: Int,
    )

    public fun setDefaultAccountIdInDataStore(
        defaultAccountId: Int,
    )
}
