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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddCategoryScreenUIStateDelegateImpl(
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
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    override val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val emoji: MutableStateFlow<String> = MutableStateFlow(
        value = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
    )
    override val selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validTransactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val screenBottomSheetType: MutableStateFlow<AddCategoryScreenBottomSheetType> =
        MutableStateFlow(
            value = AddCategoryScreenBottomSheetType.None,
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
    override fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    override fun insertCategory(
        coroutineScope: CoroutineScope,
    ) {
        val category = Category(
            emoji = emoji.value,
            title = title.value.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex.value],
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
        setScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
        )
    }

    override fun setEmoji(
        updatedEmoji: String,
    ) {
        emoji.update {
            updatedEmoji
        }
    }

    override fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    override fun setScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddCategoryScreenBottomSheetType
        }
    }

    override fun setSearchText(
        updatedSearchText: String,
    ) {
        searchText.update {
            updatedSearchText
        }
    }

    override fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }
    // endregion
}
