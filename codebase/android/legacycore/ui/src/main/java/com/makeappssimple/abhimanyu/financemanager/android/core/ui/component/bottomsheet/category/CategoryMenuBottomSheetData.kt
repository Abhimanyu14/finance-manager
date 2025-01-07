package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Immutable

@Immutable
public data class CategoryMenuBottomSheetData(
    val isDeleteVisible: Boolean,
    val isEditVisible: Boolean,
    val isSetAsDefaultVisible: Boolean,
)
