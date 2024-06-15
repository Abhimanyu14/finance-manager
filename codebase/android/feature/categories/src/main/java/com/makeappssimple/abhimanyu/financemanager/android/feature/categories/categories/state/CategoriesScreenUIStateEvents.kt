package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType

@Stable
internal class CategoriesScreenUIStateEvents(
    val deleteCategory: (id: Int) -> Unit = {},
    val navigateToAddCategoryScreen: (transactionType: String) -> Unit = {},
    val navigateToEditCategoryScreen: (categoryId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setCategoryIdToDelete: (updatedCategoryIdToDelete: Int?) -> Unit = {},
    val setClickedItemId: (updatedClickedItemId: Int?) -> Unit = {},
    val setDefaultCategoryIdInDataStore: (
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) -> Unit = { _, _ -> },
    val setScreenBottomSheetType: (updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType) -> Unit = {},
    val setSelectedCategoryTypeIndex: (updatedSelectedCategoryTypeIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
