package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class ViewTransactionScreenArgs(
    val transactionId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        transactionId = stringDecoder.decodeString(
            encodedString = checkNotNull(
                value = savedStateHandle.get<String>(NavigationArguments.TRANSACTION_ID),
            ),
        ).toIntOrNull(),
    )
}
