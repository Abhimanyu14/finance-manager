package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.runtime.Immutable

@Immutable
public data class MyConfirmationBottomSheetDataAndEventHandler(
    val data: MyConfirmationBottomSheetData,
    val handleEvent: (event: MyConfirmationBottomSheetEvent) -> Unit = {},
)
