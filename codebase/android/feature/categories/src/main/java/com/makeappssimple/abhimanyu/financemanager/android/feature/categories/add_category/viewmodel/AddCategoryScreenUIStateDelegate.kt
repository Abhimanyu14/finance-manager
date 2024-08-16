package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AddCategoryScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: ImmutableList<TransactionType>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val title: MutableStateFlow<TextFieldValue>
    val searchText: MutableStateFlow<String>
    val emoji: MutableStateFlow<String>
    val selectedTransactionTypeIndex: MutableStateFlow<Int>
    val screenBottomSheetType: MutableStateFlow<AddCategoryScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun clearTitle()

    fun insertCategory(
        coroutineScope: CoroutineScope,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setEmoji(
        updatedEmoji: String,
    )

    fun setTitle(
        updatedTitle: TextFieldValue,
    )

    fun setScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
    )

    fun setSearchText(
        updatedSearchText: String,
    )

    fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )
    // endregion
}
