package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.navigation

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs

internal class AddOrEditSourceScreenArgs(
    val originalSourceId: Int?,
) {
    constructor(
        savedStateHandle: SavedStateHandle,
        stringDecoder: StringDecoder,
    ) : this(
        originalSourceId = savedStateHandle.get<Int>(NavArgs.SOURCE_ID),
    )
}
