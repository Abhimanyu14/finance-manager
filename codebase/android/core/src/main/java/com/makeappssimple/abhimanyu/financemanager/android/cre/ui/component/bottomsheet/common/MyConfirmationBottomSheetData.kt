package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common

import androidx.compose.runtime.Immutable

@Immutable
public data class MyConfirmationBottomSheetData(
    val message: String,
    val negativeButtonText: String,
    val positiveButtonText: String,
    val title: String,
)
