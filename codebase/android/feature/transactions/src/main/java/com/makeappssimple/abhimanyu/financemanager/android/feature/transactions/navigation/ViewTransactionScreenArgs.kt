package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs

internal class ViewTransactionScreenArgs(
    val originalTransactionId: Int?,
) {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalTransactionId = stringDecoder.decodeString(
            encodedString = checkNotNull(
                value = savedStateHandle[NavArgs.TRANSACTION_ID],
            ),
        ).toIntOrNull(),
    )
}
