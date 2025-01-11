package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenArgs

internal class EditTransactionForScreenArgs(
    val transactionForId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        @Suppress("UNUSED_PARAMETER") stringDecoder: StringDecoder,
    ) : this(
        transactionForId = savedStateHandle.get<Int>(NavArgs.TRANSACTION_FOR_ID),
    )
}
