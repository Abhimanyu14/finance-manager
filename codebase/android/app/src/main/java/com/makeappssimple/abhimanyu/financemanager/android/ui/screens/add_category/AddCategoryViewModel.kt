package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddCategoryViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val transactionTypes: List<TransactionType>
    val title: StateFlow<String>
    val selectedTransactionTypeIndex: StateFlow<Int>
    val emoji: StateFlow<String>
    val emojis: StateFlow<List<Emoji>>

    fun insertCategory()

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
}
