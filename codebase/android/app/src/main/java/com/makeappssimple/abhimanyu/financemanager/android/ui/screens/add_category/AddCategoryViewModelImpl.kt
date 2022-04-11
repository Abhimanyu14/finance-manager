package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val loadingEmoji = "⏳"
const val loadingCompletedEmoji = "😃"

@HiltViewModel
class AddCategoryViewModelImpl @Inject constructor(
    getEmojisUseCase: GetEmojisUseCase,
    override val navigationManager: NavigationManager,
    private val insertCategoryUseCase: InsertCategoryUseCase,
) : AddCategoryViewModel, ViewModel() {
    override val transactionTypes: List<TransactionType> = TransactionType.values()
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
        value = loadingEmoji,
    )

    override val description: StateFlow<String> = _description
    override val title: StateFlow<String> = _title
    override val selectedTransactionTypeIndex: StateFlow<Int> = _selectedTransactionTypeIndex
    override val emoji: StateFlow<String> = _emoji
    override val emojis: StateFlow<List<Emoji>> = getEmojisUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertCategory() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            insertCategoryUseCase(
                category = Category(
                    description = description.value,
                    emoji = emoji.value,
                    title = title.value,
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun isValidCategoryData(): Boolean {
        return title.value.isNotNullOrBlank()
    }

    override fun clearDescription() {
        _description.value = ""
    }

    override fun updateDescription(
        updatedDescription: String,
    ) {
        _description.value = updatedDescription
    }

    override fun clearTitle() {
        _title.value = ""
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        _title.value = updatedTitle
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedTransactionTypeIndex.value = updatedIndex
    }

    override fun updateEmoji(
        updatedEmoji: String,
    ) {
        _emoji.value = updatedEmoji
    }
}
