package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.loadingCompletedEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModelImpl

@Composable
fun AddCategoryScreen(
    screenViewModel: AddOrEditCategoryScreenViewModel = hiltViewModel<AddOrEditCategoryScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddCategoryScreen",
    )
    val selectedTransactionTypeIndex: Int by screenViewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()
    val emojiGroups: Map<String, List<Emoji>> by screenViewModel.emojiGroups.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
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
        key1 = emojiGroups,
    ) {
        if (emojiGroups.isNotEmpty()) {
            screenViewModel.updateEmoji(
                updatedEmoji = loadingCompletedEmoji,
            )
        }
    }

    AddOrEditCategoryScreenView(
        data = AddOrEditCategoryScreenViewData(
            appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
            emojiGroups = emojiGroups,
            transactionTypes = screenViewModel.transactionTypes,
            navigationManager = screenViewModel.navigationManager,
            emoji = emoji,
            searchText = searchText,
            title = title,
            clearTitle = screenViewModel::clearTitle,
            isValidCategoryData = screenViewModel::isValidCategoryData,
            onCtaButtonClick = screenViewModel::insertCategory,
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
