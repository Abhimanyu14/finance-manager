package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs

internal class AddOrEditTransactionForScreenArgs(
    val originalTransactionForId: Int?,
) {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalTransactionForId = savedStateHandle.get<Int>(NavArgs.TRANSACTION_FOR_ID),
    )
}
