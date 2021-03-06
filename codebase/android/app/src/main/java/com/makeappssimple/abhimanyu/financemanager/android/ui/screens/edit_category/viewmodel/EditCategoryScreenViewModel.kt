package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EditCategoryScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
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
