package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class EditAccountScreenArgs(
    val currentAccountId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : this(
        currentAccountId = savedStateHandle.get<Int>(NavigationArguments.ACCOUNT_ID),
    )
}
