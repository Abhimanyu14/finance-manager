package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class EditTransactionForScreenArgs(
    val transactionForId: Int?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        @Suppress("UNUSED_PARAMETER") stringDecoder: StringDecoder,
    ) : this(
        transactionForId = savedStateHandle.get<Int>(NavigationArguments.TRANSACTION_FOR_ID),
    )
}
