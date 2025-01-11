package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddCategoryScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigator: Navigator,
) : AddCategoryScreenUIStateDelegate {
    // region initial data
    override val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.INVESTMENT,
    )
    // endregion

    // region UI state
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override var title = TextFieldValue()
        set(value) {
            field = value
            refresh()
        }
    override var searchText = ""
        set(value) {
            field = value
            refresh()
        }
    override var emoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
        set(value) {
            field = value
            refresh()
        }
    override var selectedTransactionTypeIndex = validTransactionTypes
        .indexOf(
            element = TransactionType.EXPENSE,
        )
        set(value) {
            field = value
            refresh()
        }
    override var screenBottomSheetType: AddCategoryScreenBottomSheetType =
        AddCategoryScreenBottomSheetType.None
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
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
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
    override fun clearSearchText() {
        searchText = ""
    }

    override fun clearTitle() {
        title = title.copy(
            text = "",
        )
    }

    override fun insertCategory() {
        val category = Category(
            emoji = emoji,
            title = title.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex],
        )
        coroutineScope.launch {
            insertCategoriesUseCase(category)
            navigator.navigateUp()
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
        )
    }

    override fun updateEmoji(
        updatedEmoji: String,
    ) {
        emoji = updatedEmoji
    }

    override fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
    ) {
        screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
    }

    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText = updatedSearchText
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title = updatedTitle
    }
    // endregion
}
