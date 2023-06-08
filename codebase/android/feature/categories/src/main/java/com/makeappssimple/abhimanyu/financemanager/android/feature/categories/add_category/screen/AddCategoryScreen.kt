package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModelImpl

@Composable
fun AddCategoryScreen(
    screenViewModel: AddOrEditCategoryScreenViewModel = hiltViewModel<AddOrEditCategoryScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AddCategoryScreen",
    )

    val screenUIData: AddOrEditCategoryScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = screenUIData?.emojiGroups,
    ) {
        if (screenUIData?.emojiGroups?.isNotEmpty() == true) {
            screenViewModel.updateEmoji(
                updatedEmoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
            )
        }
    }

    AddOrEditCategoryScreenUI(
        data = screenUIData?.copy(
            appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
        ) ?: AddOrEditCategoryScreenUIData(
            appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
        ),
        events = AddOrEditCategoryScreenUIEvents(
            clearTitle = screenViewModel::clearTitle,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::insertCategory,
            updateEmoji = screenViewModel::updateEmoji,
            updateSearchText = screenViewModel::updateSearchText,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateTitle = screenViewModel::updateTitle,
        ),
        state = rememberCommonScreenUIState(),
    )
}
