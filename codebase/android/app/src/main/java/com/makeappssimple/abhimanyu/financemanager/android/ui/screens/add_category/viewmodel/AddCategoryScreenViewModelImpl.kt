package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.constants.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isSalaryCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AddCategoryScreenViewModelImpl @Inject constructor(
    getEmojisUseCase: GetEmojisUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val insertCategoryUseCase: InsertCategoryUseCase,
) : AddCategoryScreenViewModel, ViewModel() {
    override val transactionTypes: List<TransactionType> = TransactionType.values()
        .filter {
            it != TransactionType.TRANSFER && it != TransactionType.ADJUSTMENT
        }

    private val _title = MutableStateFlow(
        value = "",
    )
    override val title: StateFlow<String> = _title

    private val _selectedTransactionTypeIndex = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val selectedTransactionTypeIndex: StateFlow<Int> = _selectedTransactionTypeIndex

    private val _emoji = MutableStateFlow(
        value = loadingEmoji,
    )
    override val emoji: StateFlow<String> = _emoji

    private val _searchText = MutableStateFlow(
        value = "",
    )
    override val searchText: StateFlow<String> = _searchText

    private val emojis: StateFlow<List<EmojiLocalEntity>> = getEmojisUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )
    override val filteredEmojis: Flow<List<EmojiLocalEntity>> = combine(
        flow = emojis,
        flow2 = searchText,
    ) { emojis, searchText ->
        emojis.filter { emoji ->
            if (searchText.isBlank()) {
                true
            } else {
                emoji.unicodeName.contains(searchText)
            }
        }
    }.flowOn(
        context = dispatcherProvider.io,
    )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertCategory() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertCategoryUseCase(
                category = Category(
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
        return title.value.isNotNullOrBlank() &&
                !isSalaryCategory(
                    category = title.value,
                ) &&
                !isDefaultCategory(
                    category = title.value,
                )
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

    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        _searchText.value = updatedSearchText
    }
}
