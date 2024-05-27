package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class CategoriesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setCategoryIdToDelete: (updatedCategoryIdToDelete: Int?) -> Unit,
    val setClickedItemId: (updatedClickedItemId: Int?) -> Unit,
    val setScreenBottomSheetType: (updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType) -> Unit,
    val setSelectedCategoryTypeIndex: (updatedSelectedCategoryTypeIndex: Int) -> Unit,
) : ScreenUIStateEvents
