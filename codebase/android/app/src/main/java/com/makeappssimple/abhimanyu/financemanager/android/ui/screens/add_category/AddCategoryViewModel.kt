package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _description = MutableStateFlow(
        value = "",
    )
    private val _title = MutableStateFlow(
        value = "",
    )
    private val _selectedTransactionTypeIndex = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    private val _emoji = MutableStateFlow(
        value = "⏳",
    )
    private val _emojis = MutableStateFlow(
        value = emptyList<Emoji>(),
    )

    val description: StateFlow<String> = _description
    val title: StateFlow<String> = _title
    val selectedTransactionTypeIndex: StateFlow<Int> = _selectedTransactionTypeIndex
    val emoji: StateFlow<String> = _emoji
    val emojis: StateFlow<List<Emoji>> = _emojis


    init {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            emojiRepository.emojis.collect {
                withContext(
                    context = Dispatchers.Main,
                ) {
                    _emojis.value = it
                    _emoji.value = "😃"
                }
            }
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
                    description = description.value,
                    title = title.value,
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    fun isValidCategoryData(): Boolean {
        return title.value.isNotNullOrBlank()
    }

    fun clearDescription() {
        _description.value = ""
    }

    fun updateDescription(
        updatedDescription: String,
    ) {
        _description.value = updatedDescription
    }

    fun clearTitle() {
        _title.value = ""
    }

    fun updateTitle(
        updatedTitle: String,
    ) {
        _title.value = updatedTitle
    }

    fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedTransactionTypeIndex.value = updatedIndex
    }

    fun updateEmoji(
        updatedEmoji: String,
    ) {
        _emoji.value = updatedEmoji
    }
}
