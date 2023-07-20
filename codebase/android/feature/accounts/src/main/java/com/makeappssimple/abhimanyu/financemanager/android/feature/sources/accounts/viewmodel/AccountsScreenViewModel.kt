package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.accounts.screen.AccountsScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AccountsScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<AccountsScreenUIData>?>

    fun deleteAccount(
        id: Int,
    )

    fun navigateToAddAccountScreen()

    fun navigateToEditAccountScreen(
        sourceId: Int,
    )

    fun navigateUp()

    fun setDefaultAccountIdInDataStore(
        defaultAccountId: Int,
    )
}
