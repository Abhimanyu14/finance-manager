package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal interface CategoriesScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: PersistentList<TransactionType>
    // endregion

    // region UI state
    val refreshSignal: MutableSharedFlow<Unit>
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: CategoriesScreenBottomSheetType
    val screenSnackbarType: CategoriesScreenSnackbarType
    val categoryIdToDelete: Int?
    val clickedItemId: Int?
    // endregion

    // region refresh
    fun refresh()
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
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

    fun setDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    )

    fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
    )

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
    )

    fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
    )

    fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
    )
    // endregion
}
