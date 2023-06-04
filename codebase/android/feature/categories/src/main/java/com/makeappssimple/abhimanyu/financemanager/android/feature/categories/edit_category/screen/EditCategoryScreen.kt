package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModelImpl

@Composable
fun EditCategoryScreen(
    screenViewModel: AddOrEditCategoryScreenViewModel = hiltViewModel<AddOrEditCategoryScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside EditCategoryScreen",
    )
    val selectedTransactionTypeIndex: Int by screenViewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()
    val emojiGroups: Map<String, List<Emoji>> by screenViewModel.emojiGroups.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val emoji: String by screenViewModel.emoji.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()
    val title: TextFieldValue by screenViewModel.title.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = emojiGroups,
    ) {
        if (emojiGroups.isNotEmpty() && emoji == EmojiConstants.HOURGLASS_NOT_DONE) {
            screenViewModel.updateEmoji(
                updatedEmoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
            )
        }
    }

    AddOrEditCategoryScreenUI(
        data = AddOrEditCategoryScreenUIData(
            appBarTitleTextStringResourceId = R.string.screen_edit_category_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_category_floating_action_button_content_description,
            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
            emojiGroups = emojiGroups,
            transactionTypes = screenViewModel.transactionTypes,
            emoji = emoji,
            searchText = searchText,
            title = title,
        ),
        events = AddOrEditCategoryScreenUIEvents(
            clearTitle = screenViewModel::clearTitle,
            isValidCategoryData = screenViewModel::isValidCategoryData,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::updateCategory,
            updateEmoji = screenViewModel::updateEmoji,
            updateSearchText = screenViewModel::updateSearchText,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateTitle = screenViewModel::updateTitle,
        ),
        state = rememberCommonScreenUIState(),
    )
}
