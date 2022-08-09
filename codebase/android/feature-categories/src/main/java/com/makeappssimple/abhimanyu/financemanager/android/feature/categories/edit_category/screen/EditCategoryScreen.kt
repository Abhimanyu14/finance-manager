package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.loadingCompletedEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModelImpl

@Composable
fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel<EditCategoryScreenViewModelImpl>(),
    categoryId: Int?,
) {
    logError(
        message = "Inside EditCategoryScreen",
    )
    val selectedTransactionTypeIndex: Int by screenViewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()
    val emojis: List<Emoji> by screenViewModel.filteredEmojis.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val emoji: String by screenViewModel.emoji.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()
    val title: String by screenViewModel.title.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    LaunchedEffect(
        key1 = emojis,
    ) {
        if (emojis.isNotEmpty() && emoji == loadingEmoji) {
            screenViewModel.updateEmoji(
                updatedEmoji = loadingCompletedEmoji,
            )
        }
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
