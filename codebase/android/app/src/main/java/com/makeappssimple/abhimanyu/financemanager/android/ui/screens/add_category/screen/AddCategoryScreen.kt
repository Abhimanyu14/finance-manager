package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.viewmodel.AddCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.viewmodel.AddCategoryScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun AddCategoryScreen(
    screenViewModel: AddCategoryScreenViewModel = hiltViewModel<AddCategoryScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddCategoryScreen",
    )
    val selectedTransactionTypeIndex: Int by screenViewModel.selectedTransactionTypeIndex.collectAsState()
    val emojis: List<Emoji> by screenViewModel.filteredEmojis.collectAsState(
        initial = emptyList(),
    )
    val emoji: String by screenViewModel.emoji.collectAsState()
    val searchText: String by screenViewModel.searchText.collectAsState()
    val title: String by screenViewModel.title.collectAsState()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddCategoryScreenView(
        data = AddCategoryScreenViewData(
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
            insertCategory = {
                screenViewModel.insertCategory()
            },
            isValidCategoryData = {
                screenViewModel.isValidCategoryData()
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
