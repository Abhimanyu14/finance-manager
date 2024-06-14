package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Immutable
internal data class AccountsScreenUIStateEvents(
    val deleteAccount: (accountId: Int) -> Unit = {},
    val navigateToAddAccountScreen: () -> Unit = {},
    val navigateToEditAccountScreen: (accountId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setClickedItemId: (Int?) -> Unit = {},
    val setDefaultAccountIdInDataStore: (accountId: Int) -> Unit = {},
    val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents
