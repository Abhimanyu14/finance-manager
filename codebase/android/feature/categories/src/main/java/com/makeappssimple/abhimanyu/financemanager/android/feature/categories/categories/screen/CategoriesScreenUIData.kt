package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData

@Immutable
public data class CategoriesScreenUIData(
    val selectedTabIndex: Int = 0,
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> = emptyMap(),
) : ScreenUIData
