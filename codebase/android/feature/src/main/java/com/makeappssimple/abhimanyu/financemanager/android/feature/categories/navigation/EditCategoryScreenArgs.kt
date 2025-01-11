package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenArgs

internal class EditCategoryScreenArgs(
    val categoryId: Int?,
    val transactionType: String?,
) : ScreenArgs {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        categoryId = savedStateHandle.get<Int>(NavigationArguments.CATEGORY_ID),
        transactionType = savedStateHandle.get<String>(NavigationArguments.TRANSACTION_TYPE)?.let {
            stringDecoder.decodeString(
                encodedString = it,
            )
        },
    )
}
