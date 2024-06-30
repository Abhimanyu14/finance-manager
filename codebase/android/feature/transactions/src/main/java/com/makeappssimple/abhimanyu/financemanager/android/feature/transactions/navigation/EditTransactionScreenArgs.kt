package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class EditTransactionScreenArgs(
    val originalTransactionId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalTransactionId = savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            ).toIntOrNull()
        },
    )
}
