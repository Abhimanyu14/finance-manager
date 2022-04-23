package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EditCategoryScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager
    val category: StateFlow<Category?>
    val transactionTypes: List<TransactionType>
    val title: StateFlow<String>
    val selectedTransactionTypeIndex: StateFlow<Int>
    val emoji: StateFlow<String>
    val searchText: StateFlow<String>
    val filteredEmojis: Flow<List<Emoji>>

    fun updateCategory()

    fun isValidCategoryData(): Boolean

    fun clearTitle()

    fun updateTitle(
        updatedTitle: String,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    )

    fun updateEmoji(
        updatedEmoji: String,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )
}
