package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class EditAccountScreenArgs(
    val accountId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : this(
        accountId = savedStateHandle.get<Int>(NavArgs.ACCOUNT_ID),
    )
}
