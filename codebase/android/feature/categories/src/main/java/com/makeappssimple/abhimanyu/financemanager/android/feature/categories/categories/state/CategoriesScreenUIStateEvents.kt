package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType

@Stable
internal class CategoriesScreenUIStateEvents(
    val deleteCategory: () -> Unit = {},
    val navigateToAddCategoryScreen: (transactionType: String) -> Unit = {},
    val navigateToEditCategoryScreen: (categoryId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val setCategoryIdToDelete: (updatedCategoryIdToDelete: Int?) -> Unit = {},
    val setClickedItemId: (updatedClickedItemId: Int?) -> Unit = {},
    val setDefaultCategoryIdInDataStore: (selectedTabIndex: Int) -> Unit = {},
    val setScreenBottomSheetType: (updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType) -> Unit = {},
    val setScreenSnackbarType: (CategoriesScreenSnackbarType) -> Unit = {},
) : ScreenUIStateEvents
