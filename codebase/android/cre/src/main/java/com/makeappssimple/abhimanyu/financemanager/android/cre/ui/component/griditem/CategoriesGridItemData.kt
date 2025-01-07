package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.griditem

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category

public data class CategoriesGridItemData(
    val isDeleteVisible: Boolean? = null,
    val isEditVisible: Boolean? = null,
    val isSetAsDefaultVisible: Boolean? = null,
    val isSelected: Boolean,
    val category: Category,
)
