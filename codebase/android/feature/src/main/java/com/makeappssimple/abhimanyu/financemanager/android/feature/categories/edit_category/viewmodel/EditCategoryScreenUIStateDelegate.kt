package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface EditCategoryScreenUIStateDelegate {
    // region initial data
    val category: MutableStateFlow<Category?>
    val validTransactionTypes: ImmutableList<TransactionType>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val title: MutableStateFlow<TextFieldValue>
    val searchText: MutableStateFlow<String>
    val emoji: MutableStateFlow<String>
    val selectedTransactionTypeIndex: MutableStateFlow<Int>
    val screenBottomSheetType: MutableStateFlow<EditCategoryScreenBottomSheetType>
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
    fun clearTitle()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateEmoji(
        updatedEmoji: String,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateCategory()
    // endregion
}

