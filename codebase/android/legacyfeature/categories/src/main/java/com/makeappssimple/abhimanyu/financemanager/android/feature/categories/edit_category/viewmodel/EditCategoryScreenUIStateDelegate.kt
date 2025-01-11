package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
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

    fun setEmoji(
        updatedEmoji: String,
    )

    fun setTitle(
        updatedTitle: TextFieldValue,
    )

    fun setScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
    )

    fun setSearchText(
        updatedSearchText: String,
    )

    fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateCategory()
    // endregion
}

