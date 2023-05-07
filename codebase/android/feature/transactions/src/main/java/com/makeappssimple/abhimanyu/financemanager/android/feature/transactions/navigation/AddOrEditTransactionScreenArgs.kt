package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs

internal class AddOrEditTransactionScreenArgs(
    val isEdit: Boolean?,
    val originalTransactionId: Int?,
) {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        isEdit = savedStateHandle.get<Boolean>(NavArgs.EDIT),
        originalTransactionId = stringDecoder.decodeString(
            encodedString = checkNotNull(
                value = savedStateHandle.get<String>(NavArgs.TRANSACTION_ID),
            ),
        ).toIntOrNull(),
    )
}
