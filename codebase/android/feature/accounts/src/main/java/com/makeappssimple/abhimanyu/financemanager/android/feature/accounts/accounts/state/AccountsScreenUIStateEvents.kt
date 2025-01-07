package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType

@Immutable
internal data class AccountsScreenUIStateEvents(
    val deleteAccount: () -> Unit = {},
    val navigateToAddAccountScreen: () -> Unit = {},
    val navigateToEditAccountScreen: (accountId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setClickedItemId: (Int?) -> Unit = {},
    val setDefaultAccountIdInDataStore: () -> Unit = {},
    val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents
