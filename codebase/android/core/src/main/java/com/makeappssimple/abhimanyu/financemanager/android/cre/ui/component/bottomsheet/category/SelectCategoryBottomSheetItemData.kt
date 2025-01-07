package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category

@Immutable
internal data class SelectCategoryBottomSheetItemData(
    val category: Category,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)
