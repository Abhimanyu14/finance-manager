package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.local.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {
    val transactionTypes = TransactionType.values()
        .filter {
            it != TransactionType.TRANSFER
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
    /*
    TODO-Abhi: Emoji Picker
    var emoji by mutableStateOf(
        value = "⏳",
    )
    var emojis: List<Emoji> by mutableStateOf(
        value = emptyList(),
    )
    */


    init {
        /*
        TODO-Abhi: Emoji Picker
        viewModelScope.launch {

            val result = EmojiApi
                .retrofitService
                .getEmojis(
                    accessKey = BuildConfig.OPEN_EMOJI_KEY,
                )
            emojis = result
                .distinctBy {
                    it.codePoint
                }
                .filter {
                    !it.unicodeName.contains("skin tone")
                }
            emoji = "😃"

        }
        */
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
}
