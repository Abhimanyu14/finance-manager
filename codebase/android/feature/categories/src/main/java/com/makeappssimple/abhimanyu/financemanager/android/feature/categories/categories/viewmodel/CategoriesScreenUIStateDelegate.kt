package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

internal interface CategoriesScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<CategoriesScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<CategoriesScreenSnackbarType>
    val categoryIdToDelete: MutableStateFlow<Int?>
    val clickedItemId: MutableStateFlow<Int?>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun deleteCategory(
        coroutineScope: CoroutineScope,
        id: Int,
    )

    fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun setCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
    )

    fun setClickedItemId(
        updatedClickedItemId: Int?,
    )

    fun setDefaultCategoryIdInDataStore(
        coroutineScope: CoroutineScope,
        defaultCategoryId: Int,
        transactionType: TransactionType,
    )

    fun setScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
    )

    fun setScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
    )
    // endregion
}
