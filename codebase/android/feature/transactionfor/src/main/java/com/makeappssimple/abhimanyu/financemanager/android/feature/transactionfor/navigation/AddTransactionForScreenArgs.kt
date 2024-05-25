package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class AddTransactionForScreenArgs(
    val originalTransactionForId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        @Suppress("UNUSED_PARAMETER") stringDecoder: StringDecoder,
    ) : this(
        originalTransactionForId = savedStateHandle.get<Int>(NavArgs.TRANSACTION_FOR_ID),
    )
}
