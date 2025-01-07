package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

public data class CategoriesGridItemData(
    val isDeleteVisible: Boolean? = null,
    val isEditVisible: Boolean? = null,
    val isSetAsDefaultVisible: Boolean? = null,
    val isSelected: Boolean,
    val category: Category,
)
