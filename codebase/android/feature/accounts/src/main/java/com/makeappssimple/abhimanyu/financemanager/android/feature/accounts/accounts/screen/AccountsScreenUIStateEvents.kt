package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Immutable
internal data class AccountsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setClickedItemId: (Int?) -> Unit,
    val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents
