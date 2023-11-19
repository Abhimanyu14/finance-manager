package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class AddOrEditCategoryScreenArgs(
    val originalCategoryId: Int?,
    val originalTransactionType: String?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalCategoryId = savedStateHandle.get<Int>(NavArgs.CATEGORY_ID),
        originalTransactionType = savedStateHandle.get<String>(NavArgs.TRANSACTION_TYPE)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            )
        },
    )
}
