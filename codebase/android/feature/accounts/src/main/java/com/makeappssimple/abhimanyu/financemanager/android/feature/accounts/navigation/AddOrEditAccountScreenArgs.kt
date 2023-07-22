package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs

internal class AddOrEditAccountScreenArgs(
    val originalAccountId: Int?,
) {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalAccountId = savedStateHandle.get<Int>(NavArgs.ACCOUNT_ID),
    )
}
