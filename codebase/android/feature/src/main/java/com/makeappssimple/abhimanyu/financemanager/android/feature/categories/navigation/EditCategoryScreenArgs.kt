package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenArgs

internal class EditCategoryScreenArgs(
    val categoryId: Int?,
    val transactionType: String?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        categoryId = savedStateHandle.get<Int>(NavArgs.CATEGORY_ID),
        transactionType = savedStateHandle.get<String>(NavArgs.TRANSACTION_TYPE)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            )
        },
    )
}
