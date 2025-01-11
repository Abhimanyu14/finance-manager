package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenArgs

internal class EditTransactionScreenArgs(
    val transactionId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        transactionId = savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            ).toIntOrNull()
        },
    )
}
