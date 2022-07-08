package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.viewmodel.EditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.viewmodel.EditCategoryScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel<EditCategoryScreenViewModelImpl>(),
    categoryId: Int?,
) {
    logError(
        message = "Inside EditCategoryScreen",
    )
    val title: String by screenViewModel.title.collectAsState()
    val selectedTransactionTypeIndex: Int by screenViewModel.selectedTransactionTypeIndex.collectAsState()
    val emoji: String by screenViewModel.emoji.collectAsState()
    val searchText: String by screenViewModel.searchText.collectAsState()
    val emojis: List<Emoji> by screenViewModel.filteredEmojis.collectAsState(
        initial = emptyList(),
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    EditCategoryScreenView(
        data = EditCategoryScreenViewData(
            categoryId = categoryId,
            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
            emojis = emojis,
            transactionTypes = screenViewModel.transactionTypes,
            navigationManager = screenViewModel.navigationManager,
            emoji = emoji,
            searchText = searchText,
            title = title,
            clearTitle = {
                screenViewModel.clearTitle()
            },
            isValidCategoryData = {
                screenViewModel.isValidCategoryData()
            },
            updateCategory = {
                screenViewModel.updateCategory()
            },
            updateEmoji = { updatedEmoji ->
                screenViewModel.updateEmoji(
                    updatedEmoji = updatedEmoji,
                )
            },
            updateSearchText = { updatedSearchText ->
                screenViewModel.updateSearchText(
                    updatedSearchText = updatedSearchText,
                )
            },
            updateSelectedTransactionTypeIndex = { updatedIndex ->
                screenViewModel.updateSelectedTransactionTypeIndex(
                    updatedIndex = updatedIndex,
                )
            },
            updateTitle = { updatedTitle ->
                screenViewModel.updateTitle(
                    updatedTitle = updatedTitle,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
