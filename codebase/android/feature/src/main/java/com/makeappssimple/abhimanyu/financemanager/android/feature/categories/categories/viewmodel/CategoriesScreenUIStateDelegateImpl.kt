package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class CategoriesScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    private val navigator: Navigator,
) : CategoriesScreenUIStateDelegate {
    // region initial data
    override val validTransactionTypes: PersistentList<TransactionType> = persistentListOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    )
    // endregion

    // region UI state
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )
    override var isLoading: Boolean = true
        set(value) {
            field = value
            refresh()
        }
    override var screenBottomSheetType: CategoriesScreenBottomSheetType =
        CategoriesScreenBottomSheetType.None
        set(value) {
            field = value
            refresh()
        }
    override var screenSnackbarType: CategoriesScreenSnackbarType =
        CategoriesScreenSnackbarType.None
        set(value) {
            field = value
            refresh()
        }
    override var categoryIdToDelete: Int? = null
        set(value) {
            field = value
            refresh()
        }
    override var clickedItemId: Int? = null
        set(value) {
            field = value
            refresh()
        }
    // endregion

    // region refresh
    override fun refresh() {
        refreshSignal.tryEmit(Unit)
    }
    // endregion

    // region loading
    override fun startLoading() {
        isLoading = true
    }

    override fun completeLoading() {
        isLoading = false
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun deleteCategory() {
        coroutineScope.launch {
            categoryIdToDelete?.let { id ->
                deleteCategoryUseCase(
                    id = id,
                )
            }
        }
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
        navigator.navigateToAddCategoryScreen(
            transactionType = transactionType,
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
        navigator.navigateToEditCategoryScreen(
            categoryId = categoryId,
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
        )
    }

    override fun setDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    ) {
        coroutineScope.launch {
            clickedItemId?.let { clickedItemId ->
                val isSetDefaultCategorySuccessful = setDefaultCategoryUseCase(
                    defaultCategoryId = clickedItemId,
                    transactionType = validTransactionTypes[selectedTabIndex],
                )
                if (isSetDefaultCategorySuccessful) {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                    )
                } else {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategoryFailed,
                    )
                }
            }
        }
    }

    override fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
    ) {
        categoryIdToDelete = updatedCategoryIdToDelete
    }

    override fun updateClickedItemId(
        updatedClickedItemId: Int?,
    ) {
        clickedItemId = updatedClickedItemId
    }

    override fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
    ) {
        screenBottomSheetType = updatedCategoriesScreenBottomSheetType
    }

    override fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
    ) {
        screenSnackbarType = updatedCategoriesScreenSnackbarType
    }
    // endregion
}
