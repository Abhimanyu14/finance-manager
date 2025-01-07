package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class CategoryMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)
