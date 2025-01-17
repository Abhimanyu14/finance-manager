package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList

internal interface AddCategoryScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: ImmutableList<TransactionType>
    // endregion

    // region UI state
    val title: TextFieldValue
    val searchText: String
    val emoji: String
    val selectedTransactionTypeIndex: Int
    val screenBottomSheetType: AddCategoryScreenBottomSheetType
    // endregion

    // region state events
    fun clearSearchText(
        refresh: Boolean = true,
    )

    fun clearTitle(
        refresh: Boolean = true,
    )

    fun insertCategory()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateEmoji(
        updatedEmoji: String,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateSearchText(
        updatedSearchText: String,
        refresh: Boolean = true,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        refresh: Boolean = true,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
        refresh: Boolean = true,
    )
    // endregion
}
