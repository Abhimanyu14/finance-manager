package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AddCategoryScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: ImmutableList<TransactionType>
    // endregion

    // region UI state
    val refreshSignal: MutableSharedFlow<Unit>
    val isLoading: MutableStateFlow<Boolean>
    val title: TextFieldValue
    val searchText: String
    val emoji: String
    val selectedTransactionTypeIndex: Int
    val screenBottomSheetType: AddCategoryScreenBottomSheetType
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
    fun clearSearchText()

    fun clearTitle()

    fun insertCategory()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateEmoji(
        updatedEmoji: String,
    )

    fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )
    // endregion
}
