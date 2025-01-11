package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class AddTransactionScreenArgs(
    val transactionId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        transactionId = savedStateHandle.get<String>(NavigationArguments.TRANSACTION_ID)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            ).toIntOrNull()
        },
    )
}
