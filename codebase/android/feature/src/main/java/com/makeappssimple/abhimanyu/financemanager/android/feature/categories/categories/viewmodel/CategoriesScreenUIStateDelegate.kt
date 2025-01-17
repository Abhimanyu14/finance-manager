package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.PersistentList

internal interface CategoriesScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: PersistentList<TransactionType>
    // endregion

    // region UI state
    val screenBottomSheetType: CategoriesScreenBottomSheetType
    val screenSnackbarType: CategoriesScreenSnackbarType
    val categoryIdToDelete: Int?
    val clickedItemId: Int?
    // endregion

    // region state events
    fun deleteCategory()

    fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun updateDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    )

    fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
        refresh: Boolean = true,
    )

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
        refresh: Boolean = true,
    )
    // endregion
}
