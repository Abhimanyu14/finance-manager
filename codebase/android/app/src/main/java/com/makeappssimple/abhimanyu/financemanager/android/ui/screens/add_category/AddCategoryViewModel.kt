package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val categoryRepository: CategoryRepository,
    private val emojiRepository: EmojiRepository,
) : BaseViewModel() {
    val transactionTypes = TransactionType.values()
        .filter {
            it != TransactionType.TRANSFER && it != TransactionType.ADJUSTMENT
        }
    var description by mutableStateOf(
        value = "",
    )
    var title by mutableStateOf(
        value = "",
    )
    var selectedTransactionTypeIndex by mutableStateOf(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    var emoji by mutableStateOf(
        value = "⏳",
    )
    var emojis: List<Emoji> by mutableStateOf(
        value = emptyList(),
    )


    init {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            emojis = emojiRepository.getEmojis()
            emoji = "😃"
        }
    }


    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun insertCategory() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            categoryRepository.insertCategory(
                category = Category(
                    description = description,
                    title = title,
                    transactionType = transactionTypes[selectedTransactionTypeIndex],
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    fun isValidCategoryData(): Boolean {
        return title.isNotNullOrBlank()
    }
}
