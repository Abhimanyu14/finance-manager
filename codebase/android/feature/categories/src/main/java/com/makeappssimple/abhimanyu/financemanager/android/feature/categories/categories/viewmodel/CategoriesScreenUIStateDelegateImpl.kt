package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CategoriesScreenUIStateDelegateImpl(
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    private val navigator: Navigator,
) : CategoriesScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<CategoriesScreenBottomSheetType> =
        MutableStateFlow(
            value = CategoriesScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<CategoriesScreenSnackbarType> =
        MutableStateFlow(
            value = CategoriesScreenSnackbarType.None,
        )
    override val categoryIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    override val clickedItemId: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun deleteCategory(
        coroutineScope: CoroutineScope,
        id: Int,
    ) {
        coroutineScope.launch {
            deleteCategoryUseCase(
                id = id,
            )
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
        setScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
        )
    }

    override fun setCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
    ) {
        categoryIdToDelete.update {
            updatedCategoryIdToDelete
        }
    }

    override fun setClickedItemId(
        updatedClickedItemId: Int?,
    ) {
        clickedItemId.update {
            updatedClickedItemId
        }
    }

    override fun setDefaultCategoryIdInDataStore(
        coroutineScope: CoroutineScope,
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) {
        coroutineScope.launch {
            val isSetDefaultCategorySuccessful = setDefaultCategoryUseCase(
                defaultCategoryId = defaultCategoryId,
                transactionType = transactionType,
            )
            if (isSetDefaultCategorySuccessful) {
                setScreenSnackbarType(
                    updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                )
            } else {
                setScreenSnackbarType(
                    updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategoryFailed,
                )
            }
        }
    }

    override fun setScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedCategoriesScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedCategoriesScreenSnackbarType
        }
    }
    // endregion
}
