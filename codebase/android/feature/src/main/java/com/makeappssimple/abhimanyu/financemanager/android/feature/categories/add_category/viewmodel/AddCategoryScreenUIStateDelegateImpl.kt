package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AddCategoryScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigationKit: NavigationKit,
) : AddCategoryScreenUIStateDelegate, ScreenUIStateDelegateImpl() {
    // region initial data
    override val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    // endregion

    // region UI state
    override var title = TextFieldValue()
    override var searchText = ""
    override var emoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
    override var selectedTransactionTypeIndex = validTransactionTypes
        .indexOf(
            element = TransactionType.EXPENSE,
        )
    override var screenBottomSheetType: AddCategoryScreenBottomSheetType =
        AddCategoryScreenBottomSheetType.None
    // endregion

    // region state events
    override fun clearSearchText(
        refresh: Boolean,
    ) {
        searchText = ""
        if (refresh) {
            refresh()
        }
    }

    override fun clearTitle(
        refresh: Boolean,
    ) {
        title = title.copy(
            text = "",
        )
        if (refresh) {
            refresh()
        }
    }

    override fun insertCategory() {
        val category = Category(
            emoji = emoji,
            title = title.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex],
        )
        coroutineScope.launch {
            insertCategoriesUseCase(category)
            navigationKit.navigateUp()
        }
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
        )
    }

    override fun updateEmoji(
        updatedEmoji: String,
        refresh: Boolean,
    ) {
        emoji = updatedEmoji
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    override fun updateSearchText(
        updatedSearchText: String,
        refresh: Boolean,
    ) {
        searchText = updatedSearchText
        if (refresh) {
            refresh()
        }
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        refresh: Boolean,
    ) {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        if (refresh) {
            refresh()
        }
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
        refresh: Boolean,
    ) {
        title = updatedTitle
        if (refresh) {
            refresh()
        }
    }
    // endregion
}
