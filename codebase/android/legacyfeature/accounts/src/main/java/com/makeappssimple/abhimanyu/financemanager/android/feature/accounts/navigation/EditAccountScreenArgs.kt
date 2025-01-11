package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenArgs

internal class EditAccountScreenArgs(
    val currentAccountId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : this(
        currentAccountId = savedStateHandle.get<Int>(NavArgs.ACCOUNT_ID),
    )
}
