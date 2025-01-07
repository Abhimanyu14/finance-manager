package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common

import androidx.compose.runtime.Immutable

@Immutable
public data class MyBottomSheetListItemDataAndEventHandler(
    val data: MyBottomSheetListItemData,
    val handleEvent: (event: MyBottomSheetListItemEvent) -> Unit = {},
)
